package com.pem.logic.service.context;

import com.pem.core.common.applicationcontext.bean.BeanObject;
import com.pem.logic.common.context.OperationContextFactory;
import reactor.core.publisher.Flux;

public interface OperationContextService {
    OperationContextFactory getContextFactory();
    Flux<BeanObject> getAvailableContextes();
}
