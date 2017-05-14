package com.pem.core.operation.loop.condition;


import com.pem.core.context.OperationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public class DoWhileOperationImpl extends AbstractPredicateLoopOperation implements DoWhileOperation {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPredicateLoopOperation.class);

    @Override
    public Mono<OperationContext> execute(OperationContext context) {
        Assert.notNull(getPredicate(), String.format("Can`t execute %s. Condition isn't specified.", getClass()));
        Assert.notNull(getOperation(), String.format("Can`t execute %s. Operation isn't specified.", getClass()));

        LOGGER.debug("Execute operation in Context.", getClass());
        return executeWithPredicate(context, true)
                .doOnSuccess(operationContext -> LOGGER.debug("Finish execute {} in context {}.", getClass(), operationContext));
    }

}
