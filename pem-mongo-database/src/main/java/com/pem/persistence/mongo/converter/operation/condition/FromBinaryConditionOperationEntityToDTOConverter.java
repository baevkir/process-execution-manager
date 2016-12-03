package com.pem.persistence.mongo.converter.operation.condition;

import com.pem.core.converter.factory.ConverterFactory;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.condition.BinaryConditionOperationDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.calculator.common.CalculatorEntity;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.condition.BinaryConditionOperationEntity;
import com.pem.persistence.mongo.model.operation.condition.state.BooleanState;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromBinaryConditionOperationEntityToDTOConverter extends ConverterTemplateMethods implements Converter<BinaryConditionOperationDTO, BinaryConditionOperationEntity> {

    private ConverterFactory converterFactory;

    @Override
    public BinaryConditionOperationEntity convert(BinaryConditionOperationDTO source) {
        BinaryConditionOperationEntity conditionOperationEntity = new BinaryConditionOperationEntity();
        fillCommonFields(conditionOperationEntity, source);

        conditionOperationEntity.setCalculatorEntity(converterFactory.convert(source.getCalculator(), CalculatorEntity.class));

        List<BooleanState> states = new ArrayList<>();
        for (Map.Entry<Boolean, OperationDTO> sourceStates : source.getStates().entrySet()) {
            OperationEntity operationEntity = converterFactory.convert(sourceStates.getValue(), OperationEntity.class);
            BooleanState state = new BooleanState();
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
