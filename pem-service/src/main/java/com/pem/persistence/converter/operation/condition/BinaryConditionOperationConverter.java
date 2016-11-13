package com.pem.persistence.converter.operation.condition;

import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.operation.basic.Operation;
import com.pem.operation.condition.BinaryConditionOperation;
import com.pem.persistence.converter.ConverterFactory;
import com.pem.persistence.converter.common.AbstractOperationConverter;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.operation.condition.BinaryConditionOperationEntity;
import com.pem.persistence.model.operation.condition.state.BooleanState;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class BinaryConditionOperationConverter extends AbstractOperationConverter<BinaryConditionOperationEntity> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(BinaryConditionOperationEntity source) {
        BinaryConditionOperation binaryConditionOperation = getOperationProvider().createCommonOperation(BinaryConditionOperation.class);

        BinaryConditionCalculator calculator = converterFactory.convert(source.getCalculatorEntity(), BinaryConditionCalculator.class);
        binaryConditionOperation.setCalculator(calculator);

        for (BooleanState state : source.getStates() ) {
            Operation operation = converterFactory.convert(state.getOperationEntity(), Operation.class);
            binaryConditionOperation.addCondition(state.getConditionValue(), operation);
        }
        return binaryConditionOperation;
    }
}
