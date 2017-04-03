package com.pem.core.trigger;

import com.pem.core.common.Identifiable;
import com.pem.core.context.OperationContext;
import reactor.core.publisher.Mono;

public interface Trigger extends Identifiable {
    Mono<Integer> apply(Mono<OperationContext> context);
}
