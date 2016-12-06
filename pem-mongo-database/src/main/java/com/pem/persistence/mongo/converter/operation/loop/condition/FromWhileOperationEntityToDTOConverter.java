package com.pem.persistence.mongo.converter.operation.loop.condition;

import com.pem.core.converter.factory.ConverterFactory;
import com.pem.core.converter.impl.Converter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.calculator.common.CalculatorDTO;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.loop.condition.WhileLoopOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.loop.condition.WhileLoopOperationEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromWhileOperationEntityToDTOConverter extends ConverterTemplateMethods implements Converter<WhileLoopOperationEntity, WhileLoopOperationDTO> {

    private ConverterFactory converterFactory;

    @Override
    public WhileLoopOperationDTO convert(WhileLoopOperationEntity source) {
        WhileLoopOperationDTO loopOperationDTO = new WhileLoopOperationDTO();
        fillCommonFields(loopOperationDTO, source);

        loopOperationDTO.setCalculator(converterFactory.convert(source.getCalculator(), CalculatorDTO.class));
        checkActive(loopOperationDTO, loopOperationDTO.getCalculator());

        loopOperationDTO.setOperation(converterFactory.convert(source.getOperation(), OperationDTO.class));
        checkActive(loopOperationDTO, loopOperationDTO.getOperation());

        return loopOperationDTO;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
