package com.pem.model.calculator.common;

import com.pem.model.common.BaseDTOWithStatus;

public abstract class CalculatorDTO<S> extends BaseDTOWithStatus {

    @Override
    public String toString() {
        return "CalculatorDTO{} " + super.toString();
    }
}
