package com.pem.persistence.converter.operation.loop;

import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.operation.basic.Operation;
import com.pem.operation.loop.condition.DoWhileOperation;
import com.pem.persistence.converter.ConverterFactory;
import com.pem.persistence.converter.common.AbstractOperationConverter;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.operation.loop.condition.DoWhileLoopOperationEntity;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class DoWhileOperationConverter extends AbstractOperationConverter<DoWhileLoopOperationEntity> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(DoWhileLoopOperationEntity source) {
        DoWhileOperation loopOperation = getOperationProvider().createCommonOperation(DoWhileOperation.class);

        loopOperation.setCalculator(converterFactory.convert(source.getCalculator(), BinaryConditionCalculator.class));
        loopOperation.setOperation(converterFactory.convert(source.getOperation(), Operation.class));

        return loopOperation;
    }
}
