package observer;

import subject.Game;

public class HeadlineGenerator implements Subscriber 
{

	@Override
	public void update(Game game) {
		if(game.finished)
		{
			System.out.print("\nHeadline:");
			int difference = game.awayTeamScore() - game.homeTeamScore();
			if (difference == 0) {
				System.out.printf("\n%s ties %s!\n\n", game.awayTeam().getName(), game.homeTeam().getName());
			}	
			else if (difference > 40 || difference < -40) {
				System.out.printf("\n%s humiliates %s!\n\n", game.winner(), game.loser());
			}
			else if (difference > 20 || difference < -20) {
				System.out.printf("\n%s stomps %s!\n\n", game.winner(), game.loser());
			}
			else if (difference > 15 || difference < -15) {
				System.out.printf("\n%s smashes %s!\n\n", game.winner(), game.loser());
			}
			else if (difference > 10 || difference < -10) {
				System.out.printf("\n%s dunks on %s!\n\n", game.winner(), game.loser());
			}
			else if (difference >= 5 || difference <= -5) {
				System.out.printf("\n%s beats %s in close game!\n\n", game.winner(), game.loser());
			}
			else if (difference > 0 || difference < 0) {
				System.out.printf("\n%s beats %s in close game!\n\n", game.winner(), game.loser());
			}
		}
		
		
		
		
	}



	

}
