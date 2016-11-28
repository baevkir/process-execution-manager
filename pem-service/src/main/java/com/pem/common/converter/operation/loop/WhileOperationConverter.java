package com.pem.common.converter.operation.loop;

import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.operation.basic.Operation;
import com.pem.operation.loop.condition.WhileOperation;
import com.pem.common.converter.ConverterFactory;
import com.pem.common.converter.common.AbstractOperationConverter;
import com.pem.common.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.operation.loop.condition.WhileLoopOperationEntity;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class WhileOperationConverter extends AbstractOperationConverter<WhileLoopOperationEntity> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }


    @Override
    public Operation convert(WhileLoopOperationEntity source) {
        WhileOperation loopOperation = getOperationProvider().createCommonOperation(WhileOperation.class);
        loopOperation.setOperationId(String.valueOf(source.getId()));
        loopOperation.setCalculator(converterFactory.convert(source.getCalculator(), BinaryConditionCalculator.class));
        loopOperation.setOperation(converterFactory.convert(source.getOperation(), Operation.class));

        return loopOperation;
    }
}
