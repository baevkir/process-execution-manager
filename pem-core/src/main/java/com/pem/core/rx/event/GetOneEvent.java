package com.pem.core.rx.event;

import com.google.common.base.Preconditions;

import java.math.BigInteger;

public class GetOneEvent<S> extends ObservableEvent<BigInteger, S> {
    public GetOneEvent(BigInteger source) {
        super(Preconditions.checkNotNull(source, "Can't get one. Source ID equals NULL."));
    }
}
