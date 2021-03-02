package observer;

import java.util.ArrayList;
import java.util.Random;

import game.Team;
/*
 * gives a prediction of score
 */
import subject.Game;


public class ScoreForecaster implements Subscriber {
	ArrayList<Float> scoreAccuracies;
	ArrayList<Integer> outcomeAccuracies;
	
	int partialCorrects;
	int correctPredictions;
	
	final double SCOREMOD = 11.05; // used to calculate score prediction 
	final int SIMROUNDS = 100; // number of rounds to simulate when predicting score

	int awayScorePrediction;
	int homeScorePrediction;
	String outcomePrediction;
	

	
	public ScoreForecaster() {
		scoreAccuracies = new ArrayList<Float>();
		outcomeAccuracies = new ArrayList<Integer>();
		partialCorrects = 0;
		correctPredictions = 0;
	}
	
	private void checkPredictions(int away, int home) {
		if((away == awayScorePrediction) && (home == homeScorePrediction)) {
			correctPredictions ++;
		}
		else if ((away == awayScorePrediction) || (home == homeScorePrediction)){
			partialCorrects ++;
		}
	}
	
	private void setOutcomePrediction(String awayTeam, String homeTeam) {
		if(awayScorePrediction > homeScorePrediction) {
			outcomePrediction = awayTeam;
		}
		else if (awayScorePrediction < homeScorePrediction) {
			outcomePrediction = homeTeam;
		}
		else {
			outcomePrediction = "Tie game, nobody";
		}
	}
	
	private void compareOutcome(String winner) {
		if(winner.equals(outcomePrediction)) {
			outcomeAccuracies.add(1);
		}
		else {
			outcomeAccuracies.add(0);
		}
	}

	private void printMetrics() {
		System.out.printf("\n corrects: %d partial corrects: %d ", correctPredictions, partialCorrects);
	}
	
	private int predictScore(int offenseRating, int defenseRating, int quarter, int currentScore) {
		int simulatedScore = runScoreSim(offenseRating, defenseRating, quarter, currentScore);
		double scoreCalculation = calculateScore(offenseRating, defenseRating, quarter, currentScore);
		int avg = (int) (((simulatedScore * 2) + scoreCalculation) / 3);
		return avg;
	}

	private int runScoreSim(int offenseRating, int defenseRating, int quarter, int currentScore) {
		int total = 0;
		for(int i = 0; i<SIMROUNDS;i++){
			total += simulateScore(offenseRating, defenseRating, quarter, currentScore);
		}
		
		int avgTotal = total/SIMROUNDS;
		return avgTotal;
	}

	private int simulateScore(int offenseRating, int defenseRating, int quarter, int currentScore) {
		Random rand = new Random();
		int scoreSimulation = currentScore;
		
		int quartersLeft = 4 - quarter;
		for(int i = 0; i < (Game.ROUNDS*quartersLeft); i++) {
			int offenseRoll = rand.nextInt(offenseRating);
			int defenseRoll = rand.nextInt(defenseRating);
			
			if (offenseRoll >= defenseRoll) {
				if ( (offenseRoll - defenseRoll) > 2) {
					scoreSimulation += 3;
				}
				else {
					scoreSimulation += 2;
				}
			}
		}
		return scoreSimulation;
	}

	private double calculateScore(int offenseRating, int defenseRating, int quarter, int currentScore) {
		int quartersLeft = 4 - quarter;
		double scoreCalculation = currentScore;
		
		int difference = offenseRating - defenseRating;
	
		scoreCalculation += (SCOREMOD+difference)*quartersLeft;
		return scoreCalculation;
	}
	
	public float runningPredictionAccuracy() {
		float total = 0;
		for(Float dif : scoreAccuracies) {
			total += dif;
		}
		
		return 100 - (100 * (total / scoreAccuracies.size()));
	}

	public float outcomePredictionAccuracy() {
		int total = 0;
		for(int dif : outcomeAccuracies) {
			total += dif;
		}
		
		return (100 * ((float) total / outcomeAccuracies.size()));
	}

	public void update(Game game) {
		if(game.getQuarter() == 0) {
			awayScorePrediction = predictScore(game.awayTeam().getOffenseRating(),game.homeTeam().getDefenseRating(), game.getQuarter(),game.awayTeamScore());
			homeScorePrediction = predictScore(game.homeTeam().getOffenseRating(),game.awayTeam().getDefenseRating(), game.getQuarter(),game.homeTeamScore());
			setOutcomePrediction(game.awayTeamName(), game.homeTeamName());
			System.out.printf("\nPredicted score: %d - %d \n", awayScorePrediction, homeScorePrediction);
		}
		
		if(game.finished) {
			checkPredictions(game.awayTeamScore(),game.homeTeamScore());
			printMetrics();
			
			float awayDif = (float) Math.abs(1.0-((float)game.awayTeamScore()/awayScorePrediction));
			float homeDif = (float) Math.abs(1.0-((float)game.homeTeamScore()/homeScorePrediction));
			
			scoreAccuracies.add(awayDif);
			scoreAccuracies.add(homeDif);
			
			compareOutcome(game.winner());
			
			System.out.printf("\nScore Prediction Accuracy: %f percent", runningPredictionAccuracy());
			System.out.printf("\nOutcome Prediction Accuracy: %f percent\n", outcomePredictionAccuracy());

			
		}
		
		
		
		
	}



}
