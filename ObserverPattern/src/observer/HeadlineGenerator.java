package observer;

import game.Game;

public class HeadlineGenerator implements Subscriber 
{
	
	String headline;
	
	public HeadlineGenerator() {
		headline = "No headline yet... try again later\n";
	}

	@Override
	public void update(Game game) {
		if(game.finished)
		{
			setHeadline(game);
		}
	}
	
	public void printHeadline() {
		System.out.print("\nHeadline: " + headline);
	}

	private void setHeadline(Game game) {
		int difference = game.awayTeamScore() - game.homeTeamScore();
		if (difference == 0) {
			headline = String.format("%s ties %s!\n\n", game.teams.awayTeamName(), game.teams.homeTeamName());
		}	
		else if (difference > 40 || difference < -40) {
			headline = String.format("%s humiliates %s!\n\n", game.winner(), game.loser());
		}
		else if (difference > 20 || difference < -20) {
			headline = String.format("%s stomps %s!\n\n", game.winner(), game.loser());
		}
		else if (difference > 15 || difference < -15) {
			headline = String.format("%s smashes %s!\n\n", game.winner(), game.loser());
		}
		else if (difference > 10 || difference < -10) {
			headline = String.format("%s dunks on %s!\n\n", game.winner(), game.loser());
		}
		else if (difference >= 5 || difference <= -5) {
			headline = String.format("%s beats %s in close game!\n\n", game.winner(), game.loser());
		}
		else if (difference > 0 || difference < 0) {
			headline = String.format("%s beats %s in close game!\n\n", game.winner(), game.loser());
		}
	}



	

}
