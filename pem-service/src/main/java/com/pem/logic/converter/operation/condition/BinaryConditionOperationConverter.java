package com.pem.logic.converter.operation.condition;

import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.logic.converter.ConverterFactory;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.logic.converter.common.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.condition.BinaryConditionOperationDTO;
import com.pem.operation.basic.Operation;
import com.pem.operation.condition.BinaryConditionOperation;

import java.util.Map;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class BinaryConditionOperationConverter extends AbstractOperationConverter<BinaryConditionOperationDTO> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(BinaryConditionOperationDTO source) {
        BinaryConditionOperation binaryConditionOperation = getOperationProvider().createCommonOperation(BinaryConditionOperation.class);
        binaryConditionOperation.setOperationId(String.valueOf(source.getId()));

        BinaryConditionCalculator calculator = converterFactory.convert(source.getCalculatorEntity(), BinaryConditionCalculator.class);
        binaryConditionOperation.setCalculator(calculator);

        for (Map.Entry<Boolean, OperationDTO> state : source.getStates().entrySet()) {
            Operation operation = converterFactory.convert(state.getValue(), Operation.class);
            binaryConditionOperation.addCondition(state.getKey(), operation);
        }
        return binaryConditionOperation;
    }
}