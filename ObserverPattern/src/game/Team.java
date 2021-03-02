package game;

import java.util.Random;

public class Team {
	
	String name;
	int defenseRating;
	int offenseRating;
	
	public Team(String name) {
		this.name = name;
		setRatings();
		
	}
	
	private void setRatings() {
		Random rand = new Random();
		defenseRating = rand.nextInt(10)+1;
		offenseRating = rand.nextInt(10)+1;
	}

	public String getName() {
		return name;
	}
	
	public int getOffenseRating() {
		return offenseRating;
	}
	
	public int getDefenseRating() {
		return defenseRating;
	}
	
}
