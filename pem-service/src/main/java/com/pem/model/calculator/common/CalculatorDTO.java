package com.pem.model.calculator.common;

import com.pem.model.common.BaseDTO;

public abstract class CalculatorDTO<S> extends BaseDTO {

    @Override
    public String toString() {
        return "CalculatorDTO{} " + super.toString();
    }
}
