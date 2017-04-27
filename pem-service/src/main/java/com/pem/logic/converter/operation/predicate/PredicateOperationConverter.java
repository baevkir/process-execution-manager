package com.pem.logic.converter.operation.predicate;

import com.pem.core.predicate.Predicate;
import com.pem.logic.common.ServiceConstants;
import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.logic.converter.operation.common.AbstractOperationConverter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.condition.PredicateOperationObject;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.condition.predicate.PredicateOperation;

import java.util.Map;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class PredicateOperationConverter extends AbstractOperationConverter<PredicateOperationObject> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(PredicateOperationObject source) {
        PredicateOperation binaryConditionOperation = getBeanProvider().createCommonInstance(PredicateOperation.class);
        binaryConditionOperation.setId(source.getId());

        Predicate predicate = converterFactory.convert(source.getPredicate(), Predicate.class);
        binaryConditionOperation.setPredicate(predicate);

        for (Map.Entry<Boolean, OperationObject> state : source.getStates().entrySet()) {
            Operation operation = converterFactory.convert(state.getValue(), Operation.class);
            binaryConditionOperation.addCondition(state.getKey(), operation);
        }
        return binaryConditionOperation;
    }
}
