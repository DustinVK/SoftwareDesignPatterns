package game;

import java.util.Random;

public class Team {
	
	String name;
	int defenseRating;
	int offenseRating;
	int index;
	int record[];
		
	public Team(String name, int index) {
		this.name = name;
		this.index = index;
		record = new int[3];
		record[0] = 0;
		record[1] = 0;
		record[2] = 0;
		setRatings();
	}
	
	public void addWin() {
		record[0] += 1;
	}
	
	public void addLoss() {
		record[1] += 1;
	}
	
	public void addDraw() {
		record[2] += 1;
	}
	
	
	private void setRatings() {
		Random rand = new Random();
		defenseRating = rand.nextInt(8)+2;
		offenseRating = rand.nextInt(8)+2;
	}
	
	public String toString() {
		return String.format("%s (%d-%d-%d)", getName(), record[0], record[1], record[2]);
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
