package com.pem.persistence.mongo.converter.operation.loop.condition;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.calculator.common.CalculatorDTO;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.loop.condition.DoWhileLoopOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.loop.condition.DoWhileLoopOperationEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromDoWhileOperationEntityToDTOConverter extends ConverterTemplateMethods implements Converter<DoWhileLoopOperationEntity, DoWhileLoopOperationDTO> {

    private ConverterFactory converterFactory;

    @Override
    public DoWhileLoopOperationDTO convert(DoWhileLoopOperationEntity source) {
        DoWhileLoopOperationDTO loopOperationDTO = new DoWhileLoopOperationDTO();
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
