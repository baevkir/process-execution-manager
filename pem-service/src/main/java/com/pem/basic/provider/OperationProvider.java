package com.pem.basic.provider;

import com.pem.operation.basic.Operation;
import com.pem.operation.condition.calculator.ConditionCalculator;

public interface OperationProvider {

    <O extends Operation> O createBasicOperation(Class<O> operationClass);

    <O extends Operation> O createBasicOperation(String beanName, Class<O> operationClass);

    <O extends Operation> O getOperation(String beanName);

    <O extends ConditionCalculator> O getConditionCalculator(String beanName);
}
