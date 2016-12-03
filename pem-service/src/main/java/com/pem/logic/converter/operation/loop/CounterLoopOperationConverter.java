package com.pem.logic.converter.operation.loop;

import com.pem.logic.common.Constants;
import com.pem.model.operation.loop.CounterLoopOperationDTO;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.loop.counter.CounterLoopOperation;
import com.pem.logic.converter.ConverterFactory;
import com.pem.logic.converter.common.AbstractOperationConverter;
import com.pem.logic.converter.common.RegisterInConverterFactory;

@RegisterInConverterFactory(factories = Constants.CONVERTER_FACTORY_NAME)
public class CounterLoopOperationConverter extends AbstractOperationConverter<CounterLoopOperationDTO> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(CounterLoopOperationDTO source) {
        CounterLoopOperation loopOperation = getOperationProvider().createCommonOperation(CounterLoopOperation.class);
        loopOperation.setOperationId(String.valueOf(source.getId()));
        loopOperation.setCount(source.getCount());

        loopOperation.setOperation(converterFactory.convert(source.getOperation(), Operation.class));
        return loopOperation;
    }
}
