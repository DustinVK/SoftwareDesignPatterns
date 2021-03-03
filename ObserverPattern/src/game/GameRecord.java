package game;

public class GameRecord {
	String homeTeam;
	String awayTeam;
	String winner;
	String gameLog;
	int score[];
	
	public GameRecord(String away, String home, String winner, String gameLog, int score[]) {
		this.awayTeam = away;
		this.homeTeam = home;
		this.winner = winner;
		this.score = score;
		this.gameLog = gameLog;
	}
	
	public String toString() {
		//return String.format("\n%s - %s\n%d - %d\n%s wins.", awayTeam, homeTeam, score[0], score[1], winner);
		return String.format("\n%21s | %21s | %3d - %3d | %s wins.", awayTeam, homeTeam, score[0], score[1], winner);

	}
	
}
