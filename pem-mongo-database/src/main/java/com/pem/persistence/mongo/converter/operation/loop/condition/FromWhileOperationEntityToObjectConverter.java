package com.pem.persistence.mongo.converter.operation.loop.condition;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.loop.condition.WhileLoopOperationObject;
import com.pem.model.predicate.common.PredicateObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.loop.condition.WhileLoopOperationEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromWhileOperationEntityToObjectConverter extends ConverterTemplateMethods implements Converter<WhileLoopOperationEntity, WhileLoopOperationObject> {

    private ConverterFactory converterFactory;

    @Override
    public WhileLoopOperationObject convert(WhileLoopOperationEntity source) {
        WhileLoopOperationObject loopOperationDTO = new WhileLoopOperationObject();
        fillCommonFields(loopOperationDTO, source);

        loopOperationDTO.setPredicate(converterFactory.convert(source.getPredicate(), PredicateObject.class));
        checkActive(loopOperationDTO, loopOperationDTO.getPredicate());

        loopOperationDTO.setOperation(converterFactory.convert(source.getOperation(), OperationObject.class));
        checkActive(loopOperationDTO, loopOperationDTO.getOperation());

        return loopOperationDTO;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
