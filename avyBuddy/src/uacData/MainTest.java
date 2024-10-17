package uacData;

import java.io.IOException;

public class MainTest {

	static Forecast forecast;
	
	public static void main(String[] args) {
		try {
			forecast = new Forecast("salt-lake");
		} catch (IOException e) {
			forecast = new Forecast();
			e.printStackTrace();
		}
		System.out.println(forecast.getBottomLine());
		
		Route route = new Route("salt-lake", "Grizzly gulch" ,1 , 0, 1, 7);
		route.addRouteDanger(0, 0, 1, 7);
		
		boolean[] routeDangers = route.getroutePositions();
		
		for(int i = 0; i < routeDangers.length; i++) {
			if(routeDangers[i]) {
				System.out.println(DataToStringConversions.getPositionString(i/8, i%8) + ". Current danger: " + DataToStringConversions.getDangerString(route.getOverallDangerPositionsForRoute()[i]));
			}
		}
		System.out.println(route);
	}
}
