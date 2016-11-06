package com.pem.operation.condition;


import com.pem.operation.condition.calculator.BinaryConditionCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinaryConditionOperationImpl extends AbstractConditionOperation<Boolean, BinaryConditionCalculator> implements BinaryConditionOperation {
    private static final Logger LOGGER = LoggerFactory.getLogger(BinaryConditionOperationImpl.class);
}
