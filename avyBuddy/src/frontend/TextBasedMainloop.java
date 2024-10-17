package frontend;

import uacData.DataToStringConversions;
import uacData.Route;

public class TextBasedMainloop {

	public static void main(String[] args) {
		Route route = new Route("salt-lake", "Grizzly Gulch", 0, 2);
		
		System.out.println(route.getName() + " in the " + route.getRegion() + " region");
		System.out.println("Where this route passes:");
		
		int[] dangerPositions = route.getOverallDangerPositionsForRoute();
		dangerPositions[2] = 6;
		dangerPositions[3] = 7;
		for(int i = 0; i < dangerPositions.length; i++) {			
			if(dangerPositions[i] != 0) {
				DataToStringConversions.getPositionString(i/8, i%8);
				System.out.println(DataToStringConversions.getPositionString(i/8, i%8) + " where there is a " + DataToStringConversions.getDangerString(dangerPositions[i]));
			}
		}
	}

}
