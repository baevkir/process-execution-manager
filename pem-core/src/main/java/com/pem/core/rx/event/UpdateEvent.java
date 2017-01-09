package com.pem.core.rx.event;

public abstract class UpdateEvent<S> extends CompletableEvent {

    S source;

    public UpdateEvent(S source) {
        this.source = source;
    }

    public S getSource() {
        return source;
    }
}
