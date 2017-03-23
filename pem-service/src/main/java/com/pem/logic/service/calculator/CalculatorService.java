package com.pem.logic.service.calculator;

import com.pem.model.calculator.common.CalculatorDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface CalculatorService {
    Mono<CalculatorDTO> createCalculator(CalculatorDTO calculatorDTO);
    Mono<Void> updateCalculator(CalculatorDTO calculatorDTO);
    Mono<Void> deleteCalculator(BigInteger id);
    Mono<CalculatorDTO> getCalculator(BigInteger id);
    Flux<CalculatorDTO> getAllCalculators();
}
