package uacData;

import java.io.IOException;

public class MainTest {
	
	public static void main(String[] args) {
		RouteDatabase.addRoute(new Route("salt-lake", "Superior", 0, 4, 5));
		System.out.println(RouteDatabase.getRoute("Grizzly Gulch").getName());
	}
}
