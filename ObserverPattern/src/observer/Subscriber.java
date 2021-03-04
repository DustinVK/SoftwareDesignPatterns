package observer;


import game.Game;

/*
 *  Subscriber defines the basic Subscriber interface
 */

public interface Subscriber {
	public void update(Game game);
}
