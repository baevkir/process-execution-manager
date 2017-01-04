package com.pem.core.rx.event;

import com.google.common.base.Preconditions;

import java.math.BigInteger;

public class GetOneEvent<S> extends ObservableEvent<S> {
    BigInteger sourceId;

    public GetOneEvent(BigInteger sourceId) {
        Preconditions.checkNotNull(sourceId, "Can't get one %s. Source ID equals NULL.", getClass());
        this.sourceId = sourceId;
    }

    public BigInteger getSourceId() {
        return sourceId;
    }
}
