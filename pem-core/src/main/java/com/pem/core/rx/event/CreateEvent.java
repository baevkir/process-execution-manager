package com.pem.core.rx.event;

public abstract class CreateEvent<S, T> extends SingleEvent<T> {
    S source;

    public CreateEvent(S source) {
        this.source = source;
    }

    public S getSource() {
        return source;
    }
}
