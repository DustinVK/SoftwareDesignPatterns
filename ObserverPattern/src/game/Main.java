package game;

import observer.HeadlineGenerator;
import observer.RecordKeeper;
import observer.ScoreForecaster;
import subject.Game;

public class Main {
	
	static Game game;
	
	public static void playGame(int num) {
		for(int i = 0; i<num; i++) {
			game.reset();
			game.next();
			game.next();
			game.next();
			game.next();
			game.next();
		}
		
	}

	public static void main(String[] args) {
		


		TeamManager teamManager = new TeamManager();
		//teamManager.printTeams();
		game = new Game(teamManager);
		ScoreForecaster forcaster = new ScoreForecaster();
		RecordKeeper keeper = new RecordKeeper();
		HeadlineGenerator headlines = new HeadlineGenerator();
		
		game.registerSubscriber(keeper);
		game.registerSubscriber(forcaster);
		game.registerSubscriber(headlines);;
		
		playGame(1000);
		
		keeper.printRecords();
		forcaster.printOutcomeAccuracy();
		forcaster.printScoreAccuracy();
		forcaster.printMetrics();
		
		System.out.println(forcaster.getAccuracy(10, 100));
//		game.reset();
//		game.next();
//		game.next();
//		game.next();
//		game.next();
	
		//game.next();


		
//		System.out.printf("Home Team: %sHome Team Defense: %d \nHome Team Offense: %d \n"
//				+ "Away Team: %sAway Team Defense: %d \nAway Team Offense: %d \n",
//				game.teams[0].getName(), 
//				game.teams[0].getDefenseRating(), 
//				game.teams[0].getOffenseRating(), 
//				game.teams[1].getName(),
//				game.teams[1].getDefenseRating(), 
//				game.teams[1].getOffenseRating()
//				); 

	}

}
