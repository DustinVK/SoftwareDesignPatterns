package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
 * A helper class for Game to store data 
 */
public class GameHelper {
	
	public static Team[] getTeams() {
		Collections.shuffle(teamList);
		Team[] teams = new Team[2];
		Team teamHome = new Team(teamList.get(1));
		Team teamAway = new Team(teamList.get(0));
		teams[1] = teamHome;
		teams[0] = teamAway;
		
		return teams;
		
	}
	
	public static String getPlayLog(int points) {
		String log = "";
		if(points == 0) {
			Collections.shuffle(zeroPointLogs);	
			log = zeroPointLogs.get(0);
		}
		else if (points == 2) {
			Collections.shuffle(twoPointLogs);	
			log = twoPointLogs.get(0);
		}
		else {
			Collections.shuffle(threePointLogs);
			log = threePointLogs.get(0);
		}
		return log;
	}
	
	public static String gameResultsToString(String winner, int awayScore, int homeScore) {
		String log = String.format("\nGame over. %s wins! \nFinal Score %d - %d\n", winner, awayScore, homeScore);
		return log;
	}
	
	private static ArrayList<String> teamList = new ArrayList<String>(Arrays.asList(
			  "Boston"
			, "Brooklyn"
			, "New York "
			, "Philadelphia"
			, "Toronto"
			, "San Francisco"
			, "Los Angeles"
			, "Pheonix "
			, "Sacramento "
			, "Chicago "
			, "Cleveland"
			, "Detroit"
			, "Indiana"
			, "Milwaukee"
			, "Dallas"
			, "Houston"
			, "Memphis"
			, "New Orleans"
			, "San Antonio"
			, "Atlanta"
			, "Charlotte"
			, "Miami"
			, "Orlando"
			, "Washington"
			, "Denver"
			, "Minnesota"
			, "Oklahoma"
			, "Utah"
			, "Portland"
	));
	
	
	public static ArrayList<String> zeroPointLogs = new ArrayList<String>(Arrays.asList(
			"%s missed a shot.\n",
			"Bad pass by %s.\n",
			"Driving layup missed by %s.\n",
			"%s has the ball stolen from them\n"));

	public static ArrayList<String> twoPointLogs = new ArrayList<String>(Arrays.asList(
			"%s dunks the ball.\n",
			"2 point shot made by %s.\n",
			"Driving layup made by %s.\n"));
	
	public static ArrayList<String>threePointLogs = new ArrayList<String>(Arrays.asList(
			"Middle court 3-point shot made by %s.\n",
			"3 point shot made by %s.\n",
			"%s makes a wild 3 pointer from the other side of the court.\n"));
}
