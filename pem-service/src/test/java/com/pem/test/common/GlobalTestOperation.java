package com.pem.test.common;

import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.AbstractOperation;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.bean.provider.operation.impl.OperationBean;
import org.springframework.context.annotation.Scope;
import reactor.core.publisher.Mono;


@OperationBean(value = "Test global operation.", all = true)
@Scope(scopeName = "prototype")
public class GlobalTestOperation extends AbstractOperation implements Operation {

    @Override
    public Mono<OperationContext> execute(Mono<OperationContext> context) {
        return context;
    }
}
