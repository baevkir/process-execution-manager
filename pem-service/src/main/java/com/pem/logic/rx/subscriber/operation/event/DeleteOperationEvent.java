package com.pem.logic.rx.subscriber.operation.event;

import com.pem.core.rx.event.DeleteEvent;
import com.pem.model.operation.common.OperationDTO;

import java.math.BigInteger;

public class DeleteOperationEvent extends DeleteEvent<OperationDTO> {

    public DeleteOperationEvent(BigInteger sourceId) {
        super(sourceId);
    }
}
