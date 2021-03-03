# SoftwareDesignPatterns

## Observer Pattern 
Adjusted implementation of observer pattern to fit the given project structure:
* Scoring Interface acts as the Subject interface that defines method structure for notifying and managing Observers
* Subscriber Interface acts as the Observer interface that defines the update method structure for being notified by a Scoring class 
* Game is implements Scoring

There are currently 3 classes which implment the Subscriber interface:
* HeadlineGenerator - generates a headline based on the last finished scoring game
* RecordKeeper - Maintains a list of GameRecords and associated methods
* ScoreForecaster - Makes predictions of the final score; one prediction for each quarter. 
