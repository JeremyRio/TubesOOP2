package com.aetherwars.model.folder;

public interface EventBroker {
    public void sendEvent(Publisher publisher, Event event);
    public void addPublisher(Publisher publisher);
    public void removeComponent(Object o);
    public void addSubscriber(Publisher publisher, Subscriber subscriber);
}
