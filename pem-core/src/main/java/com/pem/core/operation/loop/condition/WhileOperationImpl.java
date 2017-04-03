package com.pem.core.operation.loop.condition;

import com.pem.core.context.OperationContext;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

public class WhileOperationImpl extends AbstractPredicateLoopOperation implements WhileOperation {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhileOperationImpl.class);

    @Override
    public Mono<OperationContext> execute(Mono<OperationContext> context) {
        Assert.notNull(getPredicate(), String.format("Can`t execute %s. Condition isn't specified.", getClass()));
        Assert.notNull(getOperation(), String.format("Can`t execute %s. Operation isn't specified.", getClass()));

        return getPredicate().apply(context)
                .doOnNext(result -> LOGGER.debug("Predicate result: {}.", result))
                .filter(result -> BooleanUtils.isTrue(result))
                .doOnNext(result -> LOGGER.debug("Start to execute operation."))
                .flatMap(result -> getOperation().execute(context)).single()
                .repeatWhenEmpty(longFlux -> longFlux);
    }

}
