package subject;

import java.util.ArrayList;
import java.util.Random;

import game.GameHelper;
import game.Team;

/*
 * Game simulates a basketball game between two teams
 */

import observer.Subscriber;

public class Game implements Scoring {
	
	public final static int ROUNDS = 8; // number of scoring attempt rounds in each quarter
	
	Team[] teams;
	int[] score;
	int quarter;
	String gameLog;
	public boolean finished;
	
	ArrayList<Subscriber> subscribers;
	
	public Game() {
		subscribers = new ArrayList<Subscriber>();
		reset();
	}
	
	public void reset() {
		setTeams();
		setQuarter(0);
		resetScore();
		
		gameLog = "";
		finished = false;
		System.out.println("-------------------------------------------");
		System.out.printf("\nNew Game!\nAway Team: %s \nHome Team: %s\n", awayTeamName(), homeTeamName());
	}
	
	public void next() {
		String log = "";
		notifySubscribers();
		if (finished) {
			log = GameHelper.gameResultsToString(winner(), awayTeamScore(), homeTeamScore());
		}
		else {
			
			log = play();
			if (quarter == 3) {
				log = play();
				finished = true;
			}
		}
		
		gameLog += log;
		System.out.print(log);
		

	}
	
	public String winner() {
		if(finished) {
			if (homeTeamScore() > awayTeamScore()) {
				return homeTeam().getName();
			}
			else if (awayTeamScore() > homeTeamScore()) {
				return awayTeam().getName();
			}
			else {
				return "Tie game, nobody";
			}
		}
		else {
			return "Game still in progress...";
		}
		
	}
	
	public String loser() {
		if (homeTeamScore() > awayTeamScore()) {
			return awayTeam().getName();
		}
		else if (awayTeamScore() > homeTeamScore()) {
			return homeTeam().getName();
		}
		else {
			return "Tie game, nobody";
		}
	}
	
	private String play() {
		
		setQuarter(quarter + 1);
		String playLog = String.format("\nQuarter: %d\n", getQuarter());

		for(int i = 0; i< ROUNDS; i++) {
			//System.out.printf("%s attempt:\n ", homeTeam().getName());
			int homeScore = scoreAttempt(homeTeam().getOffenseRating(), awayTeam().getDefenseRating());
			//System.out.printf("%s attempt:\n ", awayTeam().getName());
			int awayScore = scoreAttempt(awayTeam().getOffenseRating(), homeTeam().getDefenseRating());
			updateScore(awayScore, homeScore);
			playLog += String.format(GameHelper.getPlayLog(homeScore), homeTeam().getName());
			playLog += String.format(GameHelper.getPlayLog(awayScore), awayTeam().getName());
			
		}
		playLog += String.format("\nScore: %s \n", prettyScore());
		
		return playLog;
	}
	
	// Simulates game play - 
	// each team makes an offense and defense 'roll' based on their skill level to determine points scored or not 
	private int scoreAttempt(int offense, int defense) {
		Random rand = new Random();
		int offenseRoll = rand.nextInt(offense);
		int defenseRoll = rand.nextInt(defense);
		int scoreAttempt = 0;
		
		if (offenseRoll >= defenseRoll) {
			if ( (offenseRoll - defenseRoll) > 2) {
				scoreAttempt = 3;
			}
			else {
				scoreAttempt = 2;
			}
		}
		
		return scoreAttempt;
	}
	
	
	public int getQuarter() {
		return quarter;
	}
	
	public Team homeTeam() {
		return teams[1];
	}
	
	public String homeTeamName() {
		return teams[1].getName();
	}
	
	public Team awayTeam() {
		return teams[0];
	}
	
	public String awayTeamName() {
		return teams[0].getName();
	}
	
	public int[] score() {
		return score;
	}
	
	public String prettyScore() {
		return String.format("%d - %d", awayTeamScore(), homeTeamScore());
	}
	
	private void updateScore (int away, int home) {
		score[0] += away;
		score[1] += home;
	}
	
	private void resetScore() {
		score = new int[2];
		score[0] = 0;
		score[1] = 0;
		
	}
	
	public int homeTeamScore() {
		return score[1];
	}
	
	public int awayTeamScore() {
		return score[0];
	}
	
	private void setQuarter(int q) {
		quarter = q;
	}
	
	private void setTeams() {
		teams = GameHelper.getTeams();
	}

	// Scoring Interface Methods 
	@Override
	public void registerSubscriber(Subscriber subscriber) {
		// avoid duplicate references in arrayList
		if(!subscribers.contains(subscriber)) {
			subscribers.add(subscriber);
		}
		else {
			System.out.println("Extra subscriber not added!");
		}
	}

	@Override
	public void unregisterSubscriber(Subscriber subscriber) {
		subscribers.remove(subscriber);

	}

	@Override
	public void notifySubscribers() {
		for (Subscriber subscriber : subscribers) {
			subscriber.update(this);
		}

	}

}
