package com.pem.common.converter.operation.condition;

import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.operation.basic.Operation;
import com.pem.operation.condition.BinaryConditionOperation;
import com.pem.common.converter.ConverterFactory;
import com.pem.common.converter.common.AbstractOperationConverter;
import com.pem.common.converter.common.RegisterInConverterFactory;
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
        binaryConditionOperation.setOperationId(String.valueOf(source.getId()));

        BinaryConditionCalculator calculator = converterFactory.convert(source.getCalculatorEntity(), BinaryConditionCalculator.class);
        binaryConditionOperation.setCalculator(calculator);

        for (BooleanState state : source.getStates() ) {
            Operation operation = converterFactory.convert(state.getOperationEntity(), Operation.class);
            binaryConditionOperation.addCondition(state.getConditionValue(), operation);
        }
        return binaryConditionOperation;
    }
}
