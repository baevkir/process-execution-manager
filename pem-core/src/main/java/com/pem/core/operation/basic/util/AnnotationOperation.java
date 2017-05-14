package com.pem.core.operation.basic.util;

import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.AbstractOperation;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.basic.util.reflection.AnnotationOperationInvoker;
import com.pem.core.operation.basic.util.reflection.AnnotationOperationInvokerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public abstract class AnnotationOperation extends AbstractOperation implements Operation {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationOperation.class);

    private AnnotationOperationInvoker operationInvoker;

    @Override
    public Mono<OperationContext> execute(OperationContext context) {
        return Mono.just(context)
                .doOnNext(operationContext -> LOGGER.trace("Start to execute Operation {}.", getClass()))
                .map(operationContext -> getInvoker().invoke(operationContext));

    }

    protected AnnotationOperationInvoker createInvoker() {
        return new AnnotationOperationInvokerImpl(this);
    }

    private AnnotationOperationInvoker getInvoker() {
        if (operationInvoker == null) {
            operationInvoker = createInvoker();
        }
        return operationInvoker;
    }
}
