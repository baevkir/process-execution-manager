package com.pem.logic.converter.operation.loop;

import com.pem.core.predicate.Predicate;
import com.pem.logic.common.ServiceConstants;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.loop.condition.WhileOperation;
import com.pem.core.common.converter.ConverterFactory;
import com.pem.logic.converter.operation.common.AbstractOperationConverter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.model.operation.loop.condition.WhileLoopOperationObject;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class WhileOperationConverter extends AbstractOperationConverter<WhileLoopOperationObject> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }


    @Override
    public Operation convert(WhileLoopOperationObject source) {
        WhileOperation loopOperation = getBeanProvider().createCommonInstance(WhileOperation.class);
        loopOperation.setId(source.getId());
        loopOperation.setPredicate(converterFactory.convert(source.getPredicate(), Predicate.class));
        loopOperation.setOperation(converterFactory.convert(source.getOperation(), Operation.class));

        return loopOperation;
    }
}
