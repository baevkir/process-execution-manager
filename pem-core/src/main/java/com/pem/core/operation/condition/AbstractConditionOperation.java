package com.pem.core.operation.condition;

import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.AbstractOperation;
import com.pem.core.operation.basic.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractConditionOperation<C> extends AbstractOperation implements ConditionOperation<C> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConditionOperation.class);

    private final Map<C, Operation> conditions = new HashMap<>();

    protected abstract Mono<C> calculateCondition(OperationContext context);

    @Override
    public Mono<OperationContext> execute(OperationContext context) {
        return calculateCondition(context)
                .doOnSuccess(condition -> Assert.notNull(condition, String.format("Can't apply trigger for %s.", getClass())))
                .map(condition -> getOperationForCondition(condition))
                .flatMap(operation -> operation.execute(context))
                .single();
    }

    @Override
    public void addCondition(C condition, Operation operation) {
        conditions.put(condition, operation);
    }

    private Operation getOperationForCondition(C condition) {
        LOGGER.debug("Start to find Operation for trigger {}.", condition);
        Operation operation = conditions.get(condition);
        Assert.notNull(operation, String.format("Can't find Operation for Condition %s.", condition));
        return operation;
    }

}
