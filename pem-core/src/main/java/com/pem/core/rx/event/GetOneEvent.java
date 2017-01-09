package com.pem.core.rx.event;

import java.math.BigInteger;

public class GetOneEvent<T> extends SingleEvent<T> {
    BigInteger sourceId;

    public GetOneEvent(BigInteger sourceId) {
        this.sourceId = sourceId;
    }

    public BigInteger getSourceId() {
        return sourceId;
    }
}
