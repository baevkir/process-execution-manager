package com.pem.logic.converter.operation.loop;

import com.pem.core.conditioncalculator.BinaryConditionCalculator;
import com.pem.logic.common.ServiceConstants;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.loop.condition.WhileOperation;
import com.pem.core.converter.factory.ConverterFactory;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.loop.condition.WhileLoopOperationObject;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class WhileOperationConverter extends AbstractOperationConverter<WhileLoopOperationObject> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }


    @Override
    public Operation convert(WhileLoopOperationObject source) {
        WhileOperation loopOperation = getOperationProvider().createCommonOperation(WhileOperation.class);
        loopOperation.setOperationId(String.valueOf(source.getId()));
        loopOperation.setCalculator(converterFactory.convert(source.getCalculator(), BinaryConditionCalculator.class));
        loopOperation.setOperation(converterFactory.convert(source.getOperation(), Operation.class));

        return loopOperation;
    }
}
