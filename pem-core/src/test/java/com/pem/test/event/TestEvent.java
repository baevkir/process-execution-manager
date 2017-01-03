package com.pem.test.event;

import com.pem.core.rx.event.BaseEvent;

public class TestEvent extends BaseEvent {
    boolean eventHandled = false;

    public boolean isEventHandled() {
        return eventHandled;
    }

    public void setEventHandled(boolean eventHandled) {
        this.eventHandled = eventHandled;
    }
}
