package subject;

import observer.Subscriber;

public interface Scoring {
    public void registerSubscriber(Subscriber subscriber); 
    public void unregisterSubscriber(Subscriber subscriber);
    public void notifySubscribers();
}
