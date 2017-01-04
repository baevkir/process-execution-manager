package com.pem.core.rx.event;

import java.math.BigInteger;

public abstract class DeleteEvent<S> extends ObservableEvent<S> {
    private BigInteger sourceId;

    public DeleteEvent(BigInteger sourceId) {
        this.sourceId = sourceId;
    }

    public BigInteger getSourceId() {
        return sourceId;
    }
}
