package com.pem.persistence.mongo.converter.operation.loop.condition;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.loop.condition.DoWhileLoopOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.calculator.common.CalculatorEntity;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.loop.condition.DoWhileLoopOperationEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromDoWhileOperationDTOToEntityConverter extends ConverterTemplateMethods implements Converter<DoWhileLoopOperationDTO, DoWhileLoopOperationEntity> {

    private ConverterFactory converterFactory;

    @Override
    public DoWhileLoopOperationEntity convert(DoWhileLoopOperationDTO source) {
        DoWhileLoopOperationEntity loopOperationEntity = new DoWhileLoopOperationEntity();
        fillCommonFields(loopOperationEntity, source);

        loopOperationEntity.setCalculator(converterFactory.convert(source.getCalculator(), CalculatorEntity.class));

        loopOperationEntity.setOperation(converterFactory.convert(source.getOperation(), OperationEntity.class));

        return loopOperationEntity;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
