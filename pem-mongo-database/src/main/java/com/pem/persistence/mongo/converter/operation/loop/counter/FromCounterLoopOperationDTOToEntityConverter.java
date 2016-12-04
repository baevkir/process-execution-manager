package com.pem.persistence.mongo.converter.operation.loop.counter;

import com.pem.core.converter.factory.ConverterFactory;
import com.pem.core.converter.impl.Converter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.loop.CounterLoopOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.loop.CounterLoopOperationEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromCounterLoopOperationDTOToEntityConverter extends ConverterTemplateMethods implements Converter<CounterLoopOperationDTO, CounterLoopOperationEntity> {

    private ConverterFactory converterFactory;

    @Override
    public CounterLoopOperationEntity convert(CounterLoopOperationDTO source) {
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
