package com.pem.logic.converter.operation.loop;

import com.pem.core.predicate.Predicate;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.operation.loop.condition.DoWhileLoopOperationObject;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.loop.condition.DoWhileOperation;
import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.logic.converter.operation.common.AbstractOperationConverter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class DoWhileOperationConverter extends AbstractOperationConverter<DoWhileLoopOperationObject> {

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public Operation convert(DoWhileLoopOperationObject source) {
        DoWhileOperation loopOperation = getBeanProvider().createCommonInstance(DoWhileOperation.class);
        loopOperation.setId(source.getId());
        loopOperation.setPredicate(converterFactory.convert(source.getPredicate(), Predicate.class));
        loopOperation.setOperation(converterFactory.convert(source.getOperation(), Operation.class));

        return loopOperation;
    }
}
