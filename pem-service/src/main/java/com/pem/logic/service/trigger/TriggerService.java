package com.pem.logic.service.trigger;

import com.pem.model.trigger.common.TriggerObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface TriggerService {
    Mono<TriggerObject> createTrigger(TriggerObject calculatorDTO);
    Mono<Void> updateTrigger(TriggerObject calculatorDTO);
    Mono<Void> deleteTrigger(BigInteger id);
    Mono<TriggerObject> getTrigger(BigInteger id);
    Flux<TriggerObject> getAllTriggers();
}
