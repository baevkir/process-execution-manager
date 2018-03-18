package com.pem.persistence.mongo.converter.operation.trigger;

import com.pem.core.common.converter.ConverterFactory;
import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.condition.TriggerOperationObject;
import com.pem.model.trigger.common.TriggerObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.condition.state.IntegerState;
import com.pem.persistence.mongo.model.operation.condition.trigger.TriggerOperationEntity;

import java.util.HashMap;
import java.util.Map;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromTriggerOperationEntityToObjectConverter extends ConverterTemplateMethods implements Converter<TriggerOperationEntity, TriggerOperationObject> {

    private ConverterFactory converterFactory;

    @Override
    public TriggerOperationObject convert(TriggerOperationEntity source) {
        TriggerOperationObject triggerOperationObject = new TriggerOperationObject();
        fillCommonFields(triggerOperationObject, source);

        triggerOperationObject.setTrigger(converterFactory.convert(source.getTrigger(), TriggerObject.class));
        checkActive(triggerOperationObject, triggerOperationObject.getTrigger());
        Map<Integer, OperationObject> states = new HashMap<>();
        for (IntegerState sourceStates : source.getStates()) {
            OperationObject operationDTO = converterFactory.convert(sourceStates.getOperation(), OperationObject.class);
            checkActive(triggerOperationObject, operationDTO);
            states.put(sourceStates.getState(), operationDTO);
        }

        triggerOperationObject.setStates(states);

        return triggerOperationObject;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
