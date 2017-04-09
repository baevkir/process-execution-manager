package com.pem.persistence.mongo.converter.operation.predicate;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.condition.PredicateOperationObject;
import com.pem.model.predicate.common.PredicateObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.condition.predicate.PredicateOperationEntity;
import com.pem.persistence.mongo.model.operation.condition.state.BooleanState;

import java.util.HashMap;
import java.util.Map;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromPredicateOperationEntityToObjectConverter extends ConverterTemplateMethods implements Converter<PredicateOperationEntity, PredicateOperationObject> {

    private ConverterFactory converterFactory;

    @Override
    public PredicateOperationObject convert(PredicateOperationEntity source) {
        PredicateOperationObject predicateOperationObject = new PredicateOperationObject();
        fillCommonFields(predicateOperationObject, source);

        predicateOperationObject.setPredicate(converterFactory.convert(source.getPredicate(), PredicateObject.class));
        checkActive(predicateOperationObject, predicateOperationObject.getPredicate());

        Map<Boolean, OperationObject> states = new HashMap<>();
        for (BooleanState sourceStates : source.getStates()) {
            OperationObject operationObject = converterFactory.convert(sourceStates.getOperation(), OperationObject.class);
            checkActive(predicateOperationObject, operationObject);
            states.put(sourceStates.getState(), operationObject);
        }

        predicateOperationObject.setStates(states);

        return predicateOperationObject;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
