package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import subject.Game;

public class GamePlay {
	public final static int ROUNDS = 10;
	
	// simulates game play - each team attempts to score each round 
	public static String playQuarter(Game game, String playLog) {
		for(int i = 0; i< ROUNDS; i++) {
			int homeScore = scoreAttempt(game.homeTeam().getOffenseRating(), game.awayTeam().getDefenseRating());
			int awayScore = scoreAttempt(game.awayTeam().getOffenseRating(), game.homeTeam().getDefenseRating());
			game.updateScore(awayScore, homeScore);
			playLog += String.format(GamePlay.getPlayLog(homeScore), game.homeTeam().getName());
			playLog += String.format(GamePlay.getPlayLog(awayScore), game.awayTeam().getName());
		}
		return playLog;
	}
	
	// Simulates game play - 
	// each team makes an offense and defense 'roll' based on their skill level to determine points scored or not 
	public static int scoreAttempt(int offense, int defense) {
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

	public static String getPlayLog(int points) {
		String log = "";
		if(points == 0) {
			Collections.shuffle(GamePlay.zeroPointLogs);	
			log = GamePlay.zeroPointLogs.get(0);
		}
		else if (points == 2) {
			Collections.shuffle(GamePlay.twoPointLogs);	
			log = GamePlay.twoPointLogs.get(0);
		}
		else {
			Collections.shuffle(GamePlay.threePointLogs);
			log = GamePlay.threePointLogs.get(0);
		}
		return log;
	}

	public static String gameResultsToString(String winner, int awayScore, int homeScore) {
		String log = String.format("\nGame over. %s wins! \nFinal Score %d - %d\n", winner, awayScore, homeScore);
		return log;
	}

	public static ArrayList<String> zeroPointLogs = new ArrayList<String>(Arrays.asList(
	"----:%s missed a shot.\n",
	"----:Bad pass by %s.\n",
	"----:Driving layup missed by %s.\n",
	"----:%s has the ball stolen from them\n"));
	public static ArrayList<String> twoPointLogs = new ArrayList<String>(Arrays.asList(
	"2pts:%s dunks the ball.\n",
	"2pts:%s scores a 2 point shot.\n",
	"2pts:Driving layup made by %s.\n"));
	public static ArrayList<String>threePointLogs = new ArrayList<String>(Arrays.asList(
	"3pts:Middle court 3-point shot made by %s.\n",
	"3pts:%s makes a 3 point shot.\n",
	"3pts:%s makes a wild 3 pointer from the other side of the court.\n"));

}
