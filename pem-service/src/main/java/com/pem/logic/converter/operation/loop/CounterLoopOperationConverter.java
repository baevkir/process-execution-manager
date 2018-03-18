package com.pem.logic.converter.operation.loop;

import com.pem.core.common.converter.ConverterFactory;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.loop.counter.CounterLoopOperation;
import com.pem.logic.common.ServiceConstants;
import com.pem.logic.converter.operation.common.AbstractOperationConverter;
import com.pem.model.operation.loop.CounterLoopOperationObject;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class CounterLoopOperationConverter extends AbstractOperationConverter<CounterLoopOperationObject> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(CounterLoopOperationObject source) {
        CounterLoopOperation loopOperation = getBeanProvider().createCommonInstance(CounterLoopOperation.class);
        loopOperation.setId(source.getId());
        loopOperation.setCount(source.getCount());

        loopOperation.setOperation(converterFactory.convert(source.getOperation(), Operation.class));
        return loopOperation;
    }
}
