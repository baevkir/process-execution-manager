package com.pem.logic.converter.operation.condition;

import com.pem.core.trigger.Trigger;
import com.pem.logic.common.ServiceConstants;
import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.logic.converter.operation.common.AbstractOperationConverter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.condition.TriggerOperationObject;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.condition.trigger.TriggerOperation;

import java.util.Map;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class TriggerOperationConverter extends AbstractOperationConverter<TriggerOperationObject> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(TriggerOperationObject source) {
        TriggerOperation triggerOperation = getOperationProvider().createCommonOperation(TriggerOperation.class);
        triggerOperation.setId(source.getId());

        Trigger trigger = converterFactory.convert(source.getTrigger(), Trigger.class);
        triggerOperation.setTrigger(trigger);

        for (Map.Entry<Integer, OperationObject> state : source.getStates().entrySet()) {
            Operation operation = converterFactory.convert(state.getValue(), Operation.class);
            triggerOperation.addCondition(state.getKey(), operation);
        }
        return triggerOperation;
    }
}
