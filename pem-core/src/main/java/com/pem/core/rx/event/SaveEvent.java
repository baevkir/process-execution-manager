package com.pem.core.rx.event;

import com.google.common.base.Preconditions;

public abstract class SaveEvent<S, T> extends ObservableEvent<S, T> {
    public SaveEvent(S source) {
        super(Preconditions.checkNotNull(source, "Can't save. Source equals NULL."));
    }
}
