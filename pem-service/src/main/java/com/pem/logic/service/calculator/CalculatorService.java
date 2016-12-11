package com.pem.logic.service.calculator;

import com.pem.model.calculator.common.CalculatorDTO;

import java.math.BigInteger;
import java.util.List;

public interface CalculatorService {
    CalculatorDTO createCalculator(CalculatorDTO calculatorDTO);
    void updateCalculator(CalculatorDTO calculatorDTO);
    void deleteCalculator(BigInteger id);
    CalculatorDTO getCalculator(BigInteger id);
    List<CalculatorDTO> getAllCalculators();
}
