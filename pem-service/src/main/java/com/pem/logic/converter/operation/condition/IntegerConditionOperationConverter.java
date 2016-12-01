package com.pem.logic.converter.operation.condition;

import com.pem.conditioncalculator.IntegerConditionCalculator;
import com.pem.operation.basic.Operation;
import com.pem.operation.condition.IntegerConditionOperation;
import com.pem.logic.converter.ConverterFactory;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.logic.converter.common.RegisterInConverterFactory;
import com.pem.persistence.api.model.operation.condition.IntegerConditionOperationObject;
import com.pem.persistence.api.model.operation.condition.state.IntegerState;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class IntegerConditionOperationConverter extends AbstractOperationConverter<IntegerConditionOperationObject> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(IntegerConditionOperationObject source) {
        IntegerConditionOperation conditionOperation = getOperationProvider().createCommonOperation(IntegerConditionOperation.class);
        conditionOperation.setOperationId(String.valueOf(source.getId()));

        IntegerConditionCalculator calculator = converterFactory.convert(source.getCalculatorEntity(), IntegerConditionCalculator.class);
        conditionOperation.setCalculator(calculator);

        for (IntegerState state : source.getStates() ) {
            Operation operation = converterFactory.convert(state.getOperationEntity(), Operation.class);
            conditionOperation.addCondition(state.getConditionValue(), operation);
        }
        return conditionOperation;
    }
}
