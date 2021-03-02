package game;

import java.util.Random;

import observer.HeadlineGenerator;
import observer.RecordKeeper;
import observer.ScoreForecaster;
import subject.Game;

public class Main {

	public static void main(String[] args) {
		
		Random rand = new Random();
		

		
		Game game = new Game();
		ScoreForecaster forcaster = new ScoreForecaster();
		RecordKeeper keeper = new RecordKeeper();
		HeadlineGenerator headlines = new HeadlineGenerator();
		
		game.registerSubscriber(keeper);
		game.registerSubscriber(forcaster);
		game.registerSubscriber(headlines);;
		
		game.next();
		game.next();
		game.next();
		game.next();
		int i =0;
		while(i < 1000) {
			i++;
			game.reset();
			game.next();
			game.next();
			game.next();
			game.next();
		}
		game.reset();
		game.next();
		game.next();
		game.next();
		game.next();
	
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
