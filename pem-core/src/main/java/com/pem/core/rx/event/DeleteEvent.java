package com.pem.core.rx.event;

import java.math.BigInteger;

public abstract class DeleteEvent extends CompletableEvent {

    private BigInteger sourceId;

    public DeleteEvent(BigInteger sourceId) {
        this.sourceId = sourceId;
    }

    public BigInteger getSourceId() {
        return sourceId;
    }
}
