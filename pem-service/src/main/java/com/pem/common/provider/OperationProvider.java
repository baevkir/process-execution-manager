package com.pem.common.provider;

import com.pem.conditioncalculator.ConditionCalculator;
import com.pem.operation.basic.Operation;

import java.util.Map;

public interface OperationProvider {
    <O extends Operation> O createBasicOperation(Class<O> operationClass);

    <O extends Operation> O createBasicOperation(String beanName, Class<O> operationClass);

    <O extends Operation> O getOperation(String beanName);

    <C extends ConditionCalculator> C getConditionCalculator(String beanName);

    Map<String, Operation> getAllGlobalOperations();

    Map<String, ConditionCalculator> getAllGlobalConditionCalculators();
}
