package observer;


import subject.Game;

/*
 *  Subscriber defines the basic Subscriber interface
 */

public interface Subscriber {
	public void update(Game game);

	//public void update(game.Game game);
}
