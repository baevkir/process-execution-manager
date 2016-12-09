package com.pem.persistence.mongo.converter.operation.loop.counter;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.loop.CounterLoopOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.loop.CounterLoopOperationEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromCounterLoopOperationEntityToDTOConverter extends ConverterTemplateMethods implements Converter<CounterLoopOperationEntity, CounterLoopOperationDTO> {

    private ConverterFactory converterFactory;

    @Override
    public CounterLoopOperationDTO convert(CounterLoopOperationEntity source) {
        CounterLoopOperationDTO loopOperationDTO = new CounterLoopOperationDTO();
        fillCommonFields(loopOperationDTO, source);

        loopOperationDTO.setCount(source.getCount());

        loopOperationDTO.setOperation(converterFactory.convert(source.getOperation(), OperationDTO.class));
        checkActive(loopOperationDTO, loopOperationDTO.getOperation());

        return loopOperationDTO;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
