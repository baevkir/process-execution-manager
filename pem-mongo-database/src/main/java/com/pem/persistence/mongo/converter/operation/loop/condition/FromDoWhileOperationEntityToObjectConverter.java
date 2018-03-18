package com.pem.persistence.mongo.converter.operation.loop.condition;

import com.pem.core.common.converter.ConverterFactory;
import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.loop.condition.DoWhileLoopOperationObject;
import com.pem.model.predicate.common.PredicateObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.loop.condition.DoWhileLoopOperationEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromDoWhileOperationEntityToObjectConverter extends ConverterTemplateMethods implements Converter<DoWhileLoopOperationEntity, DoWhileLoopOperationObject> {

    private ConverterFactory converterFactory;

    @Override
    public DoWhileLoopOperationObject convert(DoWhileLoopOperationEntity source) {
        DoWhileLoopOperationObject loopOperationDTO = new DoWhileLoopOperationObject();
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
