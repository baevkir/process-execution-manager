package com.pem.logic;

import com.pem.core.trigger.Trigger;
import com.pem.core.context.OperationContext;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class CompareFirstWithSecondCalculator implements Trigger {

    private BigInteger id;

    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public void setId(BigInteger id) {
        this.id = id;
    }

    @Override
    public Mono<Integer> apply(Mono<OperationContext> context) {
        return context.map(operationContext -> new MathOperationContext(operationContext))
                .map(mathContext -> mathContext.getFirstParam().compareTo(mathContext.getSecondParam()));
      }
}
