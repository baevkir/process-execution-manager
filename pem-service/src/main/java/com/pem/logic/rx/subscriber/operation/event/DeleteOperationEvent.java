package com.pem.logic.rx.subscriber.operation.event;

import com.pem.core.rx.event.DeleteEvent;

import java.math.BigInteger;

public class DeleteOperationEvent extends DeleteEvent<Void> {

    public DeleteOperationEvent(BigInteger sourceId) {
        super(sourceId);
    }
}
