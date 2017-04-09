package com.pem.logic.service.predicate;

import com.pem.model.predicate.common.PredicateObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface PredicateService {
    Mono<PredicateObject> createPredicate(PredicateObject calculatorDTO);
    Mono<Void> updatePredicate(PredicateObject calculatorDTO);
    Mono<Void> deletePredicate(BigInteger id);
    Mono<PredicateObject> getPredicate(BigInteger id);
    Flux<PredicateObject> getAllPredicates();
}
