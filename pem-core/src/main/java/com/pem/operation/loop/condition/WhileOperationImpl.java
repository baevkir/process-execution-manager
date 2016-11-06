package com.pem.operation.loop.condition;

import com.pem.context.OperationContext;
import org.springframework.util.Assert;

public class WhileOperationImpl extends AbstractConditionLoopOperation implements WhileOperation {

    @Override
    public void execute(OperationContext context) {
        Assert.notNull(getCalculator(), String.format("Can`t execute %s. Calculator isn't specified.", getClass()));
        Assert.notNull(getOperation(), String.format("Can`t execute %s. Operation isn't specified.", getClass()));

        while (getCalculator().calculate(context)) {
            getOperation().execute(context);
        }
    }
}
