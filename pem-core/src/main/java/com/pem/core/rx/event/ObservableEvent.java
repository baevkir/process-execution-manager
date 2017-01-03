package com.pem.core.rx.event;

import rx.Subscriber;

public abstract class ObservableEvent<O> extends BaseEvent {
    Subscriber<O> subscriber;

    public ObservableEvent(Subscriber<O> subscriber) {
        this.subscriber = subscriber;
    }

    public Subscriber<O> getSubscriber() {
        return subscriber;
    }
}
