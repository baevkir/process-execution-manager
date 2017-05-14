package com.pem.core.operation.loop.condition;

import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.AbstractOperation;
import com.pem.core.operation.basic.Operation;
import com.pem.core.predicate.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public abstract class AbstractPredicateLoopOperation extends AbstractOperation implements PredicateLoopOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPredicateLoopOperation.class);
    private Operation operation;
    private Predicate predicate;

    @Override
    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    protected Operation getOperation() {
        return operation;
    }

    protected Predicate getPredicate() {
        return predicate;
    }

    protected Mono<OperationContext> executeWithPredicate(OperationContext context, boolean state){
        if (!state) {
            return Mono.just(context);
        }
        return getOperation().execute(context)
                .flatMap(operationContext -> getPredicate().apply(operationContext))
                .flatMap(result -> executeWithPredicate(context, result))
                .single();
    }
}
