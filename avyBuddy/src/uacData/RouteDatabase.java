package uacData;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class manages the connection to the database and allows for adding of routes to
 * said database
 * 
 * @author Aiden Pickett
 * @version 10/18/24
 */
public class RouteDatabase {
	
	//connection string to database
	static final String url = "jdbc:sqlite:C:\\Users\\ka7nq\\git\\avyBuddy\\avyBuddy\\avyBuddyData.db";
	
	/**
	 * Writes state of passed route object to database for storage. This includes the name, region, and routePositions
	 * instance variables values when they are passed
	 * 
	 * @param route Route object to have state stored
	 * @return message representing if route was successfully added or not
	 */
	public static String addRoute(Route route) {
		
		//Establishes database connection
		try {
			var connection = DriverManager.getConnection(url);
			var statement = connection.createStatement();
			
			//Writes state of passed Route object to database
			String sql = "INSERT INTO routes (name, region, route_positions)"
					+ "VALUES ('" + route.getName() + "', '" + route.getRegion() + "', '" + stringedArray(route.getRoutePositions()) + "')";
			
			statement.execute(sql);
			connection.close();
			statement.close();
		} catch (SQLException e) {
			return "Unsucsessful add, there is already a route named " + route.getName();
		}
		return "Sucsessful add, new route " + route.getName() + " has been added.";
	}
	
	/**
	 * Method that gets the route that corresponds with the name provided
	 * 
	 * @param routeName name of the route to get
	 * @return route object that has same state as one saved in database
	 */
	public static Route getRoute(String routeName) {
		
		ResultSet result = null;
		try {
			var connection = DriverManager.getConnection(url);
			var statement = connection.createStatement();
			
			String sqlName = "SELECT * FROM routes WHERE name = '" + routeName + "';";
			
			result = statement.executeQuery(sqlName);
			
			String name = result.getString("name");
			if(name == null) {
				throw new IllegalArgumentException("The route " + routeName + " does not exist in the database");
			}
			String region = result.getString("region");
			boolean[] routePositions = DataToStringConversions.commaSeperatedStringToBooleanArray(result.getString("route_positions"));
			
			Route returnRoute = new Route(region, name);
			returnRoute.addNewRoutePositions(routePositions);
			
			return returnRoute;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Private helper method that converts the routePositions array found in the Route object passed into the addRoute method
	 * into a comma separated string of 0s (to represent false) and 1s (to represent true) for storage
	 * 
	 * @return comma separated String as described above
	 */
	private static String stringedArray(boolean[] routePositions) {
		String returnString = "";
		for (int i = 0; i < routePositions.length; i++) {
			if (routePositions[i]) {
				returnString += "1, ";
			} else {
				returnString += "0, ";
			}
		}
		
		return returnString.substring(0, returnString.length() - 2);
	}
	
	/**
	 * This method returns a string-type list of all the names of the routes
	 * in the database
	 * 
	 * @return String-type list described above
	 */
	public static String[] getRouteNames() {
		
		ArrayList<String> routeNames = new ArrayList<String>();
		try {
			var connection = DriverManager.getConnection(url);
			var statement = connection.createStatement();
			
			String sql = "SELECT name FROM routes;";
			
			ResultSet results = statement.executeQuery(sql);
			
			while(results.next()) {
				routeNames.add(results.getString("name"));
			}
			
			System.out.println(results);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return routeNames.toArray(new String[0]);
	}
}
