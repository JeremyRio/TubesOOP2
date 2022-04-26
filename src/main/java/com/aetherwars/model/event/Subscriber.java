package com.aetherwars.model.event;

public interface Subscriber {
    void onEvent(Event event);
}
