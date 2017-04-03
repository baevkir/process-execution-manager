package com.pem.core.operation.composite;

import com.pem.core.context.OperationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SyncCompositeOperationImp extends AbstractCompositeOperation implements SyncCompositeOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncCompositeOperationImp.class);

    @Override
    public Mono<OperationContext> execute(Mono<OperationContext> context) {
        LOGGER.debug("Start {}.", getClass());
        return Flux.fromIterable(getOperations())
                .doOnNext(operation -> LOGGER.debug("Start to execute {}", operation.getClass()))
                .flatMap(operation -> operation.execute(context))
                .doOnNext(operationContext -> LOGGER.debug("Finish execution."))
                .last()
                .doOnNext(operationContext -> LOGGER.debug("Finish {}.", getClass()));
    }
}
