package com.pem.core.predicate;

import com.pem.core.common.Identifiable;
import com.pem.core.context.OperationContext;
import reactor.core.publisher.Mono;

public interface Predicate extends Identifiable {
    Mono<Boolean> apply(OperationContext context);
}
