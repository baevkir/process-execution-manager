package com.pem.core.operation.basic;

import com.pem.core.common.Identifiable;
import com.pem.core.context.OperationContext;
import reactor.core.publisher.Mono;

public interface Operation extends Identifiable {
    Mono<OperationContext> execute(OperationContext context);
}
