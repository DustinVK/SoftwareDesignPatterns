package subject;

import java.util.ArrayList;

import game.TeamManager;
import game.GamePlay;
import game.GameRecord;
import game.Team;

/*
 * Game simulates a basketball game between two teams
 */

import observer.Subscriber;

public class Game implements Scoring {
	Team[] teams;
	TeamManager teamManager;
	int[] score;
	int quarter;
	String gameLog;
	public boolean finished;
	public GameRecord record;
	
	ArrayList<Subscriber> subscribers;
	
	public Game(TeamManager teamManager) {
		subscribers = new ArrayList<Subscriber>();
		this.teamManager = teamManager;
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
		if(!finished) {
			notifySubscribers(); // lets subscribers know game is beginning and updates them on game state
			log = play();
			if(quarter == 4) {
				log = finishGame(log);
			}
		}
		gameLog += log;
		System.out.print(log);
	}
	
	public void updateScore (int away, int home) {
		score[0] += away;
		score[1] += home;
	}
	
	// getters 
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
	
	public int homeTeamScore() {
		return score[1];
	}
	
	public int awayTeamScore() {
		return score[0];
	}

	// private methods 
	private String finishGame(String log) {
		gameLog += log;
		System.out.print(log);
		finished = true;
		record = new GameRecord(awayTeam().toString(), homeTeam().toString(), winner(), gameLog, score);
		TeamManager.updateTeamRecords(homeTeam(), awayTeam(), winner());
		log = gameResultsToString(); // updates subscribers that game has finished
		notifySubscribers();
		return log;
	}

	private String gameResultsToString() {
		if(finished) {
			String log;
			log = GamePlay.gameResultsToString(winner(), awayTeamScore(), homeTeamScore());
			return log;
		}
		else return "gameResultsToString() can't finish because game is still in progress...";
	}
	
	private String play() {
		setQuarter(quarter + 1);
		String playLog = String.format("\nQuarter: %d\n", getQuarter());
		playLog = GamePlay.playQuarter(this, playLog);
		playLog += String.format("\nScore: %s \n", prettyScore());
		return playLog;
	}
	
	
	private void resetScore() {
		score = new int[2];
		score[0] = 0;
		score[1] = 0;
		
	}
	
	private void setQuarter(int q) {
		quarter = q;
	}
	
	private void setTeams() {
		teams = TeamManager.getTeams(2);
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
