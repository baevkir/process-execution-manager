package com.pem.persistence.mongo.converter.operation.condition;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.condition.IntegerConditionOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.calculator.common.CalculatorEntity;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.condition.IntegerConditionOperationEntity;
import com.pem.persistence.mongo.model.operation.condition.state.IntegerState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromIntegerConditionOperationDTOToEntityConverter extends ConverterTemplateMethods implements Converter<IntegerConditionOperationDTO, IntegerConditionOperationEntity> {

    private ConverterFactory converterFactory;

    @Override
    public IntegerConditionOperationEntity convert(IntegerConditionOperationDTO source) {
        IntegerConditionOperationEntity conditionOperationEntity = new IntegerConditionOperationEntity();
        fillCommonFields(conditionOperationEntity, source);

        conditionOperationEntity.setCalculatorEntity(converterFactory.convert(source.getCalculator(), CalculatorEntity.class));

        List<IntegerState> states = new ArrayList<>();
        for (Map.Entry<Integer, OperationDTO> sourceStates : source.getStates().entrySet()) {
            OperationEntity operationEntity = converterFactory.convert(sourceStates.getValue(), OperationEntity.class);
            IntegerState state = new IntegerState();
            state.setConditionValue(sourceStates.getKey());
            state.setOperationEntity(operationEntity);
        }

        conditionOperationEntity.setStates(states);

        return conditionOperationEntity;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
