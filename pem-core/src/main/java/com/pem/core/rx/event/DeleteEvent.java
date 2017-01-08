package com.pem.core.rx.event;

import java.math.BigInteger;

public abstract class DeleteEvent<T> extends ObservableEvent<BigInteger, T> {
    public DeleteEvent(BigInteger source) {
        super(source);
    }
}
