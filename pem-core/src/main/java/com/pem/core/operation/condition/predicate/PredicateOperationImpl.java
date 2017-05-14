package com.pem.core.operation.condition.predicate;


import com.google.common.base.Preconditions;
import com.pem.core.predicate.Predicate;
import com.pem.core.context.OperationContext;
import com.pem.core.operation.condition.AbstractConditionOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class PredicateOperationImpl extends AbstractConditionOperation<Boolean> implements PredicateOperation {
    private static final Logger LOGGER = LoggerFactory.getLogger(PredicateOperationImpl.class);

    private Predicate predicate;

    @Override
    public void setPredicate(Predicate predicate) {
        this.predicate = Preconditions.checkNotNull(predicate);
    }

    @Override
    protected Mono<Boolean> calculateCondition(OperationContext context) {
        LOGGER.debug("Apply Predicate {} to context", predicate);
        return predicate.apply(context);
    }
}
