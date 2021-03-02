package observer;

import java.util.HashMap;

import subject.Game;

public class RecordKeeper implements Subscriber {
	
	HashMap<String, Integer> games = new HashMap<String, Integer>();
	HashMap<String, Integer> wins = new HashMap<String, Integer>();
	HashMap<String, Integer> losses = new HashMap<String, Integer>();
	HashMap<String, Integer> draws = new HashMap<String, Integer>();
	
	private int getCount(String team, HashMap<String, Integer> record) {
		if(record.containsKey(team)) {
			return record.get(team);
		}
		else {
			return 0;
		}
		
	}
	
	public int getWins(String team){
		return getCount(team, wins);	
	}
	
	public int getLosses(String team){
		return getCount(team, losses);
	}
	
	public int getDraws(String team){
		return getCount(team, draws);
		
	}
	
	public String getTeamRecord(String team) {
		return String.format("%s's record (win-loss-tie): (%d-%d-%d)", team, getWins(team), getLosses(team), getDraws(team));
	}
	
	private void updateGameRecords(Game game) {
		addHomeGame(game);
		addAwayGame(game);
		if(game.winner().equals("Tie game, nobody")) {
			draws.put(game.awayTeam().getName(), getDraws(game.awayTeam().getName())+1);
			draws.put(game.homeTeam().getName(), getDraws(game.homeTeam().getName())+1);
		}
		else {
			wins.put(game.winner(), getWins(game.winner())+1);
			losses.put(game.loser(), getLosses(game.loser())+1);
		}
	}

	private void addAwayGame(Game game) {
		if(!games.containsKey(game.awayTeamName())) {
			games.put(game.awayTeamName(), 1);
		}
		else {
			games.compute(game.awayTeamName(), (k,v) -> v+1);
			//System.out.println(games.get(game.awayTeamName()));
		}
	}

	private void addHomeGame(Game game) {
		if(!games.containsKey(game.homeTeamName())) {
			games.put(game.homeTeamName(), 1);
		}
		else {
			games.compute(game.homeTeamName(), (k,v) -> v+1);
			//System.out.println(games.get(game.homeTeamName()));
		}
	}


	@Override
	public void update(Game game) {
		if(game.getQuarter() == 0) {
			System.out.println(getTeamRecord(game.awayTeamName()));
			System.out.println(getTeamRecord(game.homeTeamName()));
			
			
		}
		else if (game.finished) {
			updateGameRecords(game);	
		}
		
	}




}
