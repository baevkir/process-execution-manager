package com.pem.logic.converter.operation.loop;

import com.pem.logic.common.ServiceConstants;
import com.pem.model.operation.loop.CounterLoopOperationDTO;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.loop.counter.CounterLoopOperation;
import com.pem.core.converter.factory.ConverterFactory;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.core.converter.impl.RegisterInConverterFactory;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class CounterLoopOperationConverter extends AbstractOperationConverter<CounterLoopOperationDTO> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(CounterLoopOperationDTO source) {
        CounterLoopOperation loopOperation = getOperationProvider().createCommonOperation(CounterLoopOperation.class);
        loopOperation.setId(source.getId());
        loopOperation.setCount(source.getCount());

        loopOperation.setOperation(converterFactory.convert(source.getOperation(), Operation.class));
        return loopOperation;
    }
}
