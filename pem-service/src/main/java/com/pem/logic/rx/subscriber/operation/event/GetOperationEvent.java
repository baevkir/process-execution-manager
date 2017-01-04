package com.pem.logic.rx.subscriber.operation.event;

import com.pem.core.rx.event.GetOneEvent;
import com.pem.model.operation.common.OperationDTO;

import java.math.BigInteger;

public class GetOperationEvent extends GetOneEvent<OperationDTO> {

    public GetOperationEvent(BigInteger sourceId) {
        super(sourceId);
    }
}
