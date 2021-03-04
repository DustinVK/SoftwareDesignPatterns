package observer;

import java.util.ArrayList;

import game.Game;
import game.GamePlay;


public class ScoreForecaster implements Subscriber {
	static int quarter;
	final double SCOREMOD = 16; // used to calculate score prediction 
	final int SIMROUNDS = 100; // number of rounds to simulate when predicting score
	ArrayList<Float> scoreAccuracies;
	ArrayList<Float> outcomeAccuracies;	
	int partialCorrects;
	int correctPredictions;
	String outcomePrediction;
	int scorePredictions[][];
	
	public ScoreForecaster() {
		scoreAccuracies = new ArrayList<Float>();
		outcomeAccuracies = new ArrayList<Float>();
		scorePredictions = new int[3][2];
		partialCorrects = 0;
		correctPredictions = 0;
	}
	
	public void update(Game game) {
		quarter = game.getQuarter();
		if(game.getQuarter() == 0) {
			initialize(game);
		}
		else if (game.getQuarter() < 4) {
			predictGameScore(game);
		}
		else {
			finish(game);
		}
	}
	
	private void checkPredictions(int away, int home) {
		for(int i =0;i<scorePredictions.length;i++) {
			if((away == awayScorePrediction(i)) && (home == homeScorePrediction(i))) {
				correctPredictions ++;
			}
			else if ((away == awayScorePrediction(i)) || (home == homeScorePrediction(i))){
				partialCorrects ++;
			}
		}
	}
	
	private void setOutcomePrediction(String awayTeam, String homeTeam) {
		if(awayScorePrediction(0) > homeScorePrediction(0)) {
			outcomePrediction = awayTeam;
		}
		else if (awayScorePrediction(0) < homeScorePrediction(0)) {
			outcomePrediction = homeTeam;
		}
		else {
			outcomePrediction = "Tie game, nobody";
		}
	}
	
	private void addOutcomeAccuracies(String winner) {
		if(winner.equals(outcomePrediction)) {
			outcomeAccuracies.add(0f);
		}
		else {
			outcomeAccuracies.add(1f);
		}
	}

	public void printMetrics() {
		System.out.printf("Correct scores predicted: %d \nPartial correct scores predicted: %d\n", correctPredictions, partialCorrects);
	}
	
	private void printPredictions(int quarter) {
		for(int i = 0;i<quarter;i++) {
			int q = i;
			int away = awayScorePrediction(q);
			int home = homeScorePrediction(q);
			System.out.printf("\nQuarter#%d prediction: %d-%d", q,away,home);
		}
		System.out.println("");
	}
	
	public int[] predictGameScore(Game game) {
		int score[] = new int[2];
		score[0] = predictIndividualScore(game.teams.awayTeam().getOffenseRating(),game.teams.homeTeam().getDefenseRating(), game.getQuarter(),game.awayTeamScore());
		score[1] = predictIndividualScore(game.teams.homeTeam().getOffenseRating(),game.teams.awayTeam().getDefenseRating(), game.getQuarter(),game.homeTeamScore());
		scorePredictions[game.getQuarter()] = score;
		return score;
	}
	
	private int predictIndividualScore(int offenseRating, int defenseRating, int quarter, int currentScore) {
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
	
	// uses GamePlay.scoreAttempt method to simulate a game
	private int simulateScore(int offenseRating, int defenseRating, int quarter, int currentScore) {
		int scoreSimulation = currentScore;
		int quartersLeft = 4 - quarter;
		for(int i = 0; i < (GamePlay.ROUNDS*quartersLeft); i++) {
			scoreSimulation += GamePlay.scoreAttempt(offenseRating, defenseRating);
		}
		return scoreSimulation;
	}
	
	// calculates an estimated game score with simple function 
	private double calculateScore(int offenseRating, int defenseRating, int quarter, int currentScore) {
		int quartersLeft = 4 - quarter;
		double scoreCalculation = currentScore;
		int difference = offenseRating - defenseRating;
		scoreCalculation += (SCOREMOD+difference)*quartersLeft;
		return scoreCalculation;
	}
	
	public float getRunningAccuracy(ArrayList<Float> list) {
		float total = 0;
		for(Float dif : list) {
			total += dif;
		}
		return 100 - (100 * (total / list.size()));
	}

	private void finish(Game game) {
		checkPredictions(game.awayTeamScore(),game.homeTeamScore());
		addScoreAccuracies(game);
		addOutcomeAccuracies(game.winner());
	}

	public void addScoreAccuracies(Game game) {
		for(int i = 0;i<scorePredictions.length;i++) {
			float awayDif = getAccuracy(game.awayTeamScore(), awayScorePrediction(i));
			float homeDif = getAccuracy(game.homeTeamScore(), homeScorePrediction(i));
			scoreAccuracies.add(awayDif);
			scoreAccuracies.add(homeDif);
		}
		
	}

	public int homeScorePrediction(int i) {
		return scorePredictions[i][1];
	}

	public int awayScorePrediction(int i) {
		return scorePredictions[i][0];
	}
	
	public float getAccuracy(int actual, int predicted) {
		float percentDif = (float) Math.abs(1.0-((float)actual/predicted));
		return percentDif;
	}

	public void printOutcomeAccuracy() {
		System.out.printf("Outcome Prediction Accuracy: %f percent\n", getRunningAccuracy(outcomeAccuracies));
	}

	public void printScoreAccuracy() {
		System.out.printf("Score Prediction Accuracy: %f percent\n", getRunningAccuracy(scoreAccuracies));
	}

	private void initialize(Game game) {
		scorePredictions = new int[4][2];
		predictGameScore(game);
		setOutcomePrediction(game.teams.awayTeamName(), game.teams.homeTeamName());
		//printPrediction(game.getQuarter());
	}

	public void printPrediction() {
		if(quarter > 3) {
			System.out.print("\nGame finished. Quarterly predictions:");
			printPredictions(4);
			
		}
		else {
			System.out.printf("Predicted score: %d - %d \n", awayScorePrediction(quarter), homeScorePrediction(quarter));
		}
		
	}
	
	public void printStats() {
		printScoreAccuracy();
		printOutcomeAccuracy();
		printMetrics();
	}



}
