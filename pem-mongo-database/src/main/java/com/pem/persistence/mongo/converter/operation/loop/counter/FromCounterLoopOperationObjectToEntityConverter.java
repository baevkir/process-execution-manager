package com.pem.persistence.mongo.converter.operation.loop.counter;

import com.pem.core.common.converter.ConverterFactory;
import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.model.operation.loop.CounterLoopOperationObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.loop.CounterLoopOperationEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromCounterLoopOperationObjectToEntityConverter extends ConverterTemplateMethods implements Converter<CounterLoopOperationObject, CounterLoopOperationEntity> {

    private ConverterFactory converterFactory;

    @Override
    public CounterLoopOperationEntity convert(CounterLoopOperationObject source) {
        CounterLoopOperationEntity counterLoopOperationEntity = new CounterLoopOperationEntity();
        fillCommonFields(counterLoopOperationEntity, source);

        counterLoopOperationEntity.setCount(source.getCount());

        counterLoopOperationEntity.setOperation(converterFactory.convert(source.getOperation(), OperationEntity.class));

        return counterLoopOperationEntity;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
