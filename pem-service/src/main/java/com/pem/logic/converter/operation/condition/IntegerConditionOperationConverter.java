package com.pem.logic.converter.operation.condition;

import com.pem.core.calculator.IntegerCalculator;
import com.pem.logic.common.ServiceConstants;
import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
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
        conditionOperation.setId(source.getId());

        IntegerCalculator calculator = converterFactory.convert(source.getCalculator(), IntegerCalculator.class);
        conditionOperation.setCalculator(calculator);

        for (Map.Entry<Integer, OperationDTO> state : source.getStates().entrySet()) {
            Operation operation = converterFactory.convert(state.getValue(), Operation.class);
            conditionOperation.addCondition(state.getKey(), operation);
        }
        return conditionOperation;
    }
}
