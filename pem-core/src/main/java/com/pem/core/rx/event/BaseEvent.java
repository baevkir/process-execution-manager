package com.pem.core.rx.event;

public abstract class BaseEvent<S> {
    private S source;

    public BaseEvent(S source) {
        this.source = source;
    }

    public S getSource() {
        return source;
    }
}
