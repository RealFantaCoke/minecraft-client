package de.crazymemecoke.manager.events.impl;

import de.crazymemecoke.manager.events.Event;

public class EventRender extends Event {

    Type type;

    public EventRender(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        twoD, threeD;
    }
}