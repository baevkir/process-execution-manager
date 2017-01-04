package com.pem.core.rx.event;

import org.springframework.util.Assert;

public abstract class SaveEvent<S> extends ObservableEvent<S> {
    S source;

    public SaveEvent(S source) {
        Assert.notNull(source, String.format("Can't save %s. Source equals NULL.", this.getClass()));
        this.source = source;
    }

    public S getSource() {
        return source;
    }
}
