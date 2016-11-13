package com.pem.persistence.converter.operation.condition;

import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.operation.basic.Operation;
import com.pem.operation.condition.BinaryConditionOperation;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.operation.condition.BinaryConditionOperationEntity;
import com.pem.persistence.model.operation.condition.state.BooleanState;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class BinaryConditionOperationConverter extends AbstractConditionOperationConverter<BinaryConditionOperationEntity> {

    @Override
    public Operation convert(BinaryConditionOperationEntity source) {
        BinaryConditionOperation binaryConditionOperation = getOperationProvider().createCommonOperation(BinaryConditionOperation.class);

        BinaryConditionCalculator calculator = getConverterFactory().convert(source.getCalculatorEntity(), BinaryConditionCalculator.class);
        binaryConditionOperation.setCalculator(calculator);

        for (BooleanState state : source.getStates() ) {
            Operation operation = getConverterFactory().convert(state.getOperationEntity(), Operation.class);
            binaryConditionOperation.addCondition(state.getConditionValue(), operation);
        }
        return binaryConditionOperation;
    }
}
