package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/*
 * A helper class for Game to store data 
 * currently uses arrayLists for shuffling convenience 
 */
public class TeamManager {
	
	private static Team[] teamArr;
	Team[] currentTeams;

	
	public TeamManager() {
		currentTeams = new Team[2];
		resetTeamArr();
		setTeams();
	}
	
	public void setTeams() {
		currentTeams = getRandomTeams(2);
	}
	
	public Team homeTeam() {
		return currentTeams[1];
	}
	
	public String homeTeamName() {
		return currentTeams[1].getName();
	}
	
	public Team awayTeam() {
		return currentTeams[0];
	}
	
	public String awayTeamName() {
		return currentTeams[0].getName();
	}
	
	public void resetTeamArr() {
		teamArr = new Team[teamList.size()];
		Collections.shuffle(teamList);
		int i = 0;
		for(String teamName: teamList) {
			Team team = new Team(teamName,i);
			teamArr[i] = team;
			i++;
		}
	}
	
	public static Team[] getRandomTeams(int quantity) {
		Team[] teams = new Team[quantity];
		Random rand = new Random();
		int index = rand.nextInt(teamList.size()-2);
		teams[0] = teamArr[index];
		teams[1] = teamArr[index +1];
		return teams;
	}
	
	public void printTeams() {
		for (int i = 0;i<teamArr.length;i++) {
			System.out.println(teamArr[i].getName());
		}
	}
	
	public static void updateTeamRecords(Team home, Team away, String winner) {
		if(home.getName().equals(winner)) {
			home.addWin();
			away.addLoss();
		}
		else if(away.getName().equals(winner)) {
			away.addWin();
			home.addLoss();
		}
		else {
			home.addDraw();
			away.addDraw();
		}
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
}
