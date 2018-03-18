package com.pem.persistence.mongo.converter.operation.loop.condition;

import com.pem.core.common.converter.ConverterFactory;
import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.model.operation.loop.condition.WhileLoopOperationObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.loop.condition.WhileLoopOperationEntity;
import com.pem.persistence.mongo.model.predicate.common.PredicateEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromWhileOperationObjectToEntityConverter extends ConverterTemplateMethods implements Converter<WhileLoopOperationObject, WhileLoopOperationEntity> {

    private ConverterFactory converterFactory;

    @Override
    public WhileLoopOperationEntity convert(WhileLoopOperationObject source) {
        WhileLoopOperationEntity loopOperationEntity = new WhileLoopOperationEntity();
        fillCommonFields(loopOperationEntity, source);

        loopOperationEntity.setPredicate(converterFactory.convert(source.getPredicate(), PredicateEntity.class));

        loopOperationEntity.setOperation(converterFactory.convert(source.getOperation(), OperationEntity.class));

        return loopOperationEntity;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
