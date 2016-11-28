package com.pem.common.converter.operation.loop;

import com.pem.operation.basic.Operation;
import com.pem.operation.loop.counter.CounterLoopOperation;
import com.pem.common.converter.ConverterFactory;
import com.pem.common.converter.common.AbstractOperationConverter;
import com.pem.common.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.operation.loop.CounterLoopOperationEntity;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class CounterLoopOperationConverter extends AbstractOperationConverter<CounterLoopOperationEntity> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(CounterLoopOperationEntity source) {
        CounterLoopOperation loopOperation = getOperationProvider().createCommonOperation(CounterLoopOperation.class);
        loopOperation.setOperationId(String.valueOf(source.getId()));
        loopOperation.setCount(source.getCount());

        loopOperation.setOperation(converterFactory.convert(source.getOperation(), Operation.class));
        return loopOperation;
    }
}
