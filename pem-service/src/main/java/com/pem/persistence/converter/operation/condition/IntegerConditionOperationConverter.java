package com.pem.persistence.converter.operation.condition;

import com.pem.conditioncalculator.IntegerConditionCalculator;
import com.pem.operation.basic.Operation;
import com.pem.operation.condition.IntegerConditionOperation;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.operation.condition.IntegerConditionOperationEntity;
import com.pem.persistence.model.operation.condition.state.IntegerState;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class IntegerConditionOperationConverter  extends AbstractConditionOperationConverter<IntegerConditionOperationEntity> {

    @Override
    public Operation convert(IntegerConditionOperationEntity source) {
        IntegerConditionOperation conditionOperation = getOperationProvider().createCommonOperation(IntegerConditionOperation.class);

        IntegerConditionCalculator calculator = getConverterFactory().convert(source.getCalculatorEntity(), IntegerConditionCalculator.class);
        conditionOperation.setCalculator(calculator);

        for (IntegerState state : source.getStates() ) {
            Operation operation = getConverterFactory().convert(state.getOperationEntity(), Operation.class);
            conditionOperation.addCondition(state.getConditionValue(), operation);
        }
        return conditionOperation;
    }
}
