package com.pem.persistence.api.service.calculator;

import com.pem.model.calculator.common.CalculatorDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface CalculatorPersistenceService {
    Mono<CalculatorDTO> createCalculator(CalculatorDTO calculator);
    Mono<Void> updateCalculator(CalculatorDTO calculator);
    Mono<CalculatorDTO> getCalculator(BigInteger id);
    Flux<CalculatorDTO> getAllCalculators();
    <C extends CalculatorDTO> Flux<C> getCalculatorsByType(final Class<C> targetClass);
    Mono<Void> deleteCalculator(BigInteger id);
}
