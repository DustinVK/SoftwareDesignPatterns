package game;

import java.util.InputMismatchException;
import java.util.Scanner;

import observer.HeadlineGenerator;
import observer.RecordKeeper;
import observer.ScoreForecaster;
import subject.Game;

public class Main {
	
	static Game game;
	static TeamManager teamManager;
	static boolean playing = true;
	
	static ScoreForecaster forcaster;
	static RecordKeeper keeper;
	static HeadlineGenerator headlines;
	
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
	
	private static boolean inputValid(int i) {
		if(i==0|| i==1 || i==2 || i==3 || i==4 || i==5 || i==6 || i==7 || i==8 || i==9) {
			return true;
		}
		return false;
	}
	
	public static void printMenus() {
		System.out.println("\n|Options: [0]Quit | [1]New Game | [2]Next Quarter | [3]Print Score\n"
				+ "[4]Print Prediction | [5]Print Prediction Stats | [6]Print Score Table\n"
				+ "[7]Print Headline | [8]Simulate 10 games | [9]Reset Session|\n");
		System.out.print("\n$:");
	}
	
	public static int getInput(Scanner input) throws InputMismatchException {
		printMenus();
		int selection = -1;
		try {
			selection = input.nextInt();
		} catch (Exception e) {
			input.next();
			selection = getInput(input);
		}
		if(!inputValid(selection)) {
			selection = getInput(input);
		}
		
		return selection;
	}
	
	private static void handleInput(int num) {
		if(num == 0) {
			playing = false;
			
		}
		else if(num == 1){
			makeNewGame();
		}
		else if(num == 2){
			if(game.finished) {
				System.out.println("Game Over... enter [1] to start a new game.");
			}
			else {
				game.next();
			}
		}
		else if(num == 3){
			System.out.println(game.prettyScore());
		}
		else if(num == 4){
			forcaster.printPrediction();
		}
		else if(num == 5){
			forcaster.printStats();
		}
		else if(num == 6){
			keeper.printRecords();
		}
		else if(num == 7){
			headlines.printHeadline();
		}
		else if(num == 8) {
			playGame(10);
		}
		else if(num == 9) {
			System.out.println("Resetting everything...");
			initialize();
		}
		else {
			System.out.println("Oops, handling input went wrong.");
		}
		
	}

	private static void makeNewGame() {
		game = new Game(teamManager);
		game.registerSubscriber(keeper);
		game.registerSubscriber(forcaster);
		game.registerSubscriber(headlines);
	}

	public static void main(String[] args) {
		System.out.println("Welcome! Setting up new game...");
		initialize();
		Scanner input = new Scanner(System.in);
		while(playing) {
			handleInput(getInput(input));
		}
		
		System.out.println("Goodbye!");
		
		

	}

	private static void initialize() {
		teamManager = new TeamManager();
		game = new Game(teamManager);
		forcaster = new ScoreForecaster();
		keeper = new RecordKeeper();
		headlines = new HeadlineGenerator();
		game.registerSubscriber(keeper);
		game.registerSubscriber(forcaster);
		game.registerSubscriber(headlines);
	}

}
