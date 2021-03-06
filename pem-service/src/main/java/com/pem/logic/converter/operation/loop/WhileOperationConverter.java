package com.pem.logic.converter.operation.loop;

import com.pem.core.calculator.BinaryCalculator;
import com.pem.logic.common.ServiceConstants;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.loop.condition.WhileOperation;
import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.loop.condition.WhileLoopOperationDTO;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class WhileOperationConverter extends AbstractOperationConverter<WhileLoopOperationDTO> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }


    @Override
    public Operation convert(WhileLoopOperationDTO source) {
        WhileOperation loopOperation = getOperationProvider().createCommonOperation(WhileOperation.class);
        loopOperation.setId(source.getId());
        loopOperation.setCalculator(converterFactory.convert(source.getCalculator(), BinaryCalculator.class));
        loopOperation.setOperation(converterFactory.convert(source.getOperation(), Operation.class));

        return loopOperation;
    }
}
