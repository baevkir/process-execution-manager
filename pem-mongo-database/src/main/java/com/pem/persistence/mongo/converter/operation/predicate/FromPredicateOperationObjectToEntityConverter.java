package com.pem.persistence.mongo.converter.operation.predicate;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.condition.PredicateOperationObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.operation.condition.predicate.PredicateOperationEntity;
import com.pem.persistence.mongo.model.operation.condition.state.BooleanState;
import com.pem.persistence.mongo.model.predicate.common.PredicateEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromPredicateOperationObjectToEntityConverter extends ConverterTemplateMethods implements Converter<PredicateOperationObject, PredicateOperationEntity> {

    private ConverterFactory converterFactory;

    @Override
    public PredicateOperationEntity convert(PredicateOperationObject source) {
        PredicateOperationEntity conditionOperationEntity = new PredicateOperationEntity();
        fillCommonFields(conditionOperationEntity, source);

        conditionOperationEntity.setPredicate(converterFactory.convert(source.getPredicate(), PredicateEntity.class));

        List<BooleanState> states = new ArrayList<>();
        for (Map.Entry<Boolean, OperationObject> sourceStates : source.getStates().entrySet()) {
            OperationEntity operationEntity = converterFactory.convert(sourceStates.getValue(), OperationEntity.class);
            BooleanState state = new BooleanState();
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
