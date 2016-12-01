package com.pem.logic.converter.operation.loop;

import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.model.operation.loop.condition.DoWhileLoopOperationDTO;
import com.pem.operation.basic.Operation;
import com.pem.operation.loop.condition.DoWhileOperation;
import com.pem.logic.converter.ConverterFactory;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.logic.converter.common.RegisterInConverterFactory;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class DoWhileOperationConverter extends AbstractOperationConverter<DoWhileLoopOperationDTO> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(DoWhileLoopOperationDTO source) {
        DoWhileOperation loopOperation = getOperationProvider().createCommonOperation(DoWhileOperation.class);
        loopOperation.setOperationId(String.valueOf(source.getId()));
        loopOperation.setCalculator(converterFactory.convert(source.getCalculator(), BinaryConditionCalculator.class));
        loopOperation.setOperation(converterFactory.convert(source.getOperation(), Operation.class));

        return loopOperation;
    }
}
