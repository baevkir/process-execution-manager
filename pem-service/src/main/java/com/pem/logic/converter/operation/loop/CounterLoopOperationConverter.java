package com.pem.logic.converter.operation.loop;

import com.pem.operation.basic.Operation;
import com.pem.operation.loop.counter.CounterLoopOperation;
import com.pem.logic.converter.ConverterFactory;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.logic.converter.common.RegisterInConverterFactory;
import com.pem.persistence.api.model.operation.loop.CounterLoopOperationObject;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class CounterLoopOperationConverter extends AbstractOperationConverter<CounterLoopOperationObject> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(CounterLoopOperationObject source) {
        CounterLoopOperation loopOperation = getOperationProvider().createCommonOperation(CounterLoopOperation.class);
        loopOperation.setOperationId(String.valueOf(source.getId()));
        loopOperation.setCount(source.getCount());

        loopOperation.setOperation(converterFactory.convert(source.getOperation(), Operation.class));
        return loopOperation;
    }
}
