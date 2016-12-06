package com.pem.persistence.mongo.converter.operation.condition;

import com.pem.core.converter.factory.ConverterFactory;
import com.pem.core.converter.impl.Converter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.calculator.common.CalculatorDTO;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.condition.IntegerConditionOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.condition.IntegerConditionOperationEntity;
import com.pem.persistence.mongo.model.operation.condition.state.IntegerState;

import java.util.HashMap;
import java.util.Map;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromIntegerConditionOperationEntityToDTOConverter extends ConverterTemplateMethods implements Converter<IntegerConditionOperationEntity, IntegerConditionOperationDTO> {

    private ConverterFactory converterFactory;

    @Override
    public IntegerConditionOperationDTO convert(IntegerConditionOperationEntity source) {
        IntegerConditionOperationDTO conditionOperationDTO = new IntegerConditionOperationDTO();
        fillCommonFields(conditionOperationDTO, source);

        conditionOperationDTO.setCalculator(converterFactory.convert(source.getCalculatorEntity(), CalculatorDTO.class));
        checkActive(conditionOperationDTO, conditionOperationDTO.getCalculator());
        Map<Integer, OperationDTO> states = new HashMap<>();
        for (IntegerState sourceStates : source.getStates()) {
            OperationDTO operationDTO = converterFactory.convert(sourceStates.getOperationEntity(), OperationDTO.class);
            checkActive(conditionOperationDTO, operationDTO);
            states.put(sourceStates.getConditionValue(), operationDTO);
        }

        conditionOperationDTO.setStates(states);

        return conditionOperationDTO;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
