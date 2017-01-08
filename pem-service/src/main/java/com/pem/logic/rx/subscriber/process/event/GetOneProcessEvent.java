package com.pem.logic.rx.subscriber.process.event;

import com.pem.core.rx.event.GetOneEvent;
import com.pem.model.proccess.ExecutionProcessDTO;

import java.math.BigInteger;

public class GetOneProcessEvent extends GetOneEvent<ExecutionProcessDTO> {
    public GetOneProcessEvent(BigInteger source) {
        super(source);
    }
}
