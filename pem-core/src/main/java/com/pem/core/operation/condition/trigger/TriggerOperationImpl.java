package com.pem.core.operation.condition.trigger;

import com.google.common.base.Preconditions;
import com.pem.core.context.OperationContext;
import com.pem.core.operation.condition.AbstractConditionOperation;
import com.pem.core.trigger.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class TriggerOperationImpl extends AbstractConditionOperation<Integer> implements TriggerOperation {
    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerOperationImpl.class);

    private Trigger trigger;

    @Override
    public void setTrigger(Trigger trigger) {
        this.trigger = Preconditions.checkNotNull(trigger);
    }

    @Override
    protected Mono<Integer> calculateCondition(Mono<OperationContext> context) {
        LOGGER.debug("Apply Trigger {} to context", trigger);
        return trigger.apply(context);
    }
}
