package observer;

import java.util.ArrayList;

import game.GameRecord;
import subject.Game;

public class RecordKeeper implements Subscriber {
	
	ArrayList<GameRecord> records;
	
	public RecordKeeper() {
		records = new ArrayList<GameRecord>();
	}
	
	public void printRecords() {
		System.out.println("--------------- Game Records ---------------");
		for(GameRecord record: records) {
			System.out.println(record.toString());
		}
		System.out.println("--------------------------------------------");
	}
	
	private void addRecord(Game game){
		if(game.finished && (!recordExists(game.record))) {
			records.add(game.record);
		}
	}
	
	private boolean recordExists(GameRecord record) {
		return records.contains(record);
	}

	@Override
	public void update(Game game) {
		addRecord(game);
	}




}
