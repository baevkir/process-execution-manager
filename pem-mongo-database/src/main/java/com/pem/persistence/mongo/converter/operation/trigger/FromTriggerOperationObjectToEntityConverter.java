package com.pem.persistence.mongo.converter.operation.trigger;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.condition.TriggerOperationObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.calculator.common.TriggerEntity;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.condition.trigger.TriggerOperationEntity;
import com.pem.persistence.mongo.model.operation.condition.state.IntegerState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromTriggerOperationObjectToEntityConverter extends ConverterTemplateMethods implements Converter<TriggerOperationObject, TriggerOperationEntity> {

    private ConverterFactory converterFactory;

    @Override
    public TriggerOperationEntity convert(TriggerOperationObject source) {
        TriggerOperationEntity conditionOperationEntity = new TriggerOperationEntity();
        fillCommonFields(conditionOperationEntity, source);

        conditionOperationEntity.setTrigger(converterFactory.convert(source.getTrigger(), TriggerEntity.class));

        List<IntegerState> states = new ArrayList<>();
        for (Map.Entry<Integer, OperationObject> sourceStates : source.getStates().entrySet()) {
            OperationEntity operationEntity = converterFactory.convert(sourceStates.getValue(), OperationEntity.class);
            IntegerState state = new IntegerState();
            state.setState(sourceStates.getKey());
            state.setOperation(operationEntity);
        }

        conditionOperationEntity.setStates(states);

        return conditionOperationEntity;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
