package com.pem.operation.condition;

import com.pem.operation.condition.calculator.IntegerConditionCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegerConditionOperationImpl extends AbstractConditionOperation<Integer, IntegerConditionCalculator> implements IntegerConditionOperation {
    private static final Logger LOGGER = LoggerFactory.getLogger(IntegerConditionOperationImpl.class);
}
