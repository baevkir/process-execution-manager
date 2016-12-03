package com.pem.logic.converter.operation.condition;

import com.pem.core.conditioncalculator.IntegerConditionCalculator;
import com.pem.logic.common.ServiceConstants;
import com.pem.core.converter.factory.ConverterFactory;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.condition.IntegerConditionOperationDTO;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.condition.IntegerConditionOperation;

import java.util.Map;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class IntegerConditionOperationConverter extends AbstractOperationConverter<IntegerConditionOperationDTO> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(IntegerConditionOperationDTO source) {
        IntegerConditionOperation conditionOperation = getOperationProvider().createCommonOperation(IntegerConditionOperation.class);
        conditionOperation.setOperationId(String.valueOf(source.getId()));

        IntegerConditionCalculator calculator = converterFactory.convert(source.getCalculatorEntity(), IntegerConditionCalculator.class);
        conditionOperation.setCalculator(calculator);

        for (Map.Entry<Integer, OperationDTO> state : source.getStates().entrySet()) {
            Operation operation = converterFactory.convert(state.getValue(), Operation.class);
            conditionOperation.addCondition(state.getKey(), operation);
        }
        return conditionOperation;
    }
}
