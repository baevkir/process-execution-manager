package com.pem.persistence.mongo.converter.operation.loop.condition;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.loop.condition.WhileLoopOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.calculator.common.CalculatorEntity;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.loop.condition.WhileLoopOperationEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromWhileOperationDTOToEntityConverter extends ConverterTemplateMethods implements Converter<WhileLoopOperationDTO, WhileLoopOperationEntity> {

    private ConverterFactory converterFactory;

    @Override
    public WhileLoopOperationEntity convert(WhileLoopOperationDTO source) {
        WhileLoopOperationEntity loopOperationEntity = new WhileLoopOperationEntity();
        fillCommonFields(loopOperationEntity, source);

        loopOperationEntity.setCalculator(converterFactory.convert(source.getCalculator(), CalculatorEntity.class));

        loopOperationEntity.setOperation(converterFactory.convert(source.getOperation(), OperationEntity.class));

        return loopOperationEntity;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
