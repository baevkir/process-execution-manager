package com.pem.persistence.mongo.converter.operation.loop.counter;

import com.pem.core.common.converter.ConverterFactory;
import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.loop.CounterLoopOperationObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.loop.CounterLoopOperationEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromCounterLoopOperationEntityToObjectConverter extends ConverterTemplateMethods implements Converter<CounterLoopOperationEntity, CounterLoopOperationObject> {

    private ConverterFactory converterFactory;

    @Override
    public CounterLoopOperationObject convert(CounterLoopOperationEntity source) {
        CounterLoopOperationObject loopOperationDTO = new CounterLoopOperationObject();
        fillCommonFields(loopOperationDTO, source);

        loopOperationDTO.setCount(source.getCount());

        loopOperationDTO.setOperation(converterFactory.convert(source.getOperation(), OperationObject.class));
        checkActive(loopOperationDTO, loopOperationDTO.getOperation());

        return loopOperationDTO;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
