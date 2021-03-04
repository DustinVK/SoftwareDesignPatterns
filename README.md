# SoftwareDesignPatterns
A project to study software architecture design patterns.  

The program simulates basketball games. Teams are assigned offense and defense ratings. Scoring is based on those ratings. A a table of previous game scores is kept during the session. There is a prediction class that attempts to predict the final score of the game before each quarter. Predictions metrics are tracked, but not currently stored.

## Observer Pattern 

Adjusted implementation of observer pattern to fit the given project structure. 
* Scoring Interface acts as the Subject interface that defines method structure for notifying and managing Observers
* Subscriber Interface acts as the Observer interface that defines the update method structure for being notified by a Scoring class 
* Game is implements Scoring

There are currently 3 classes which implment the Subscriber interface:
* HeadlineGenerator - generates a headline based on the last finished scoring game
* RecordKeeper - Maintains a list of GameRecords and associated methods
* ScoreForecaster - Makes predictions of the final score; one prediction for each quarter. 

There is a text based menu for the following: { Quit program, start a new game, play next quarter, print score,
Print prediction, print prediction stats, print table of previous games scores, print a headline, run 10 games, reset session }
