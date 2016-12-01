package com.pem.logic.converter.operation.condition;

import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.operation.basic.Operation;
import com.pem.operation.condition.BinaryConditionOperation;
import com.pem.logic.converter.ConverterFactory;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.logic.converter.common.RegisterInConverterFactory;
import com.pem.persistence.api.model.operation.condition.BinaryConditionOperationObject;
import com.pem.persistence.api.model.operation.condition.state.BooleanState;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class BinaryConditionOperationConverter extends AbstractOperationConverter<BinaryConditionOperationObject> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(BinaryConditionOperationObject source) {
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
