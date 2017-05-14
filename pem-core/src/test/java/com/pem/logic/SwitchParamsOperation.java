package com.pem.logic;

import com.pem.core.operation.basic.AbstractOperation;
import com.pem.core.operation.basic.Operation;
import com.pem.core.context.OperationContext;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class SwitchParamsOperation extends AbstractOperation implements Operation {
    @Override
    public Mono<OperationContext> execute(OperationContext context) {
        return Mono.just(context).map(operationContext -> new MathOperationContext(operationContext))
                .doOnNext(mathContext -> {
                    BigDecimal firstParam = mathContext.getFirstParam();
                    BigDecimal secondParam = mathContext.getSecondParam();

                    mathContext.setFirstParam(secondParam);
                    mathContext.setSecondParam(firstParam);
                }).cast(OperationContext.class);

    }
}
