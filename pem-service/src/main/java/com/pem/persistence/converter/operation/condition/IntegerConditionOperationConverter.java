package com.pem.persistence.converter.operation.condition;

import com.pem.conditioncalculator.IntegerConditionCalculator;
import com.pem.operation.basic.Operation;
import com.pem.operation.condition.IntegerConditionOperation;
import com.pem.persistence.converter.ConverterFactory;
import com.pem.persistence.converter.common.AbstractOperationConverter;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.operation.condition.IntegerConditionOperationEntity;
import com.pem.persistence.model.operation.condition.state.IntegerState;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class IntegerConditionOperationConverter extends AbstractOperationConverter<IntegerConditionOperationEntity> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(IntegerConditionOperationEntity source) {
        IntegerConditionOperation conditionOperation = getOperationProvider().createCommonOperation(IntegerConditionOperation.class);

        IntegerConditionCalculator calculator = converterFactory.convert(source.getCalculatorEntity(), IntegerConditionCalculator.class);
        conditionOperation.setCalculator(calculator);

        for (IntegerState state : source.getStates() ) {
            Operation operation = converterFactory.convert(state.getOperationEntity(), Operation.class);
            conditionOperation.addCondition(state.getConditionValue(), operation);
        }
        return conditionOperation;
    }
}
