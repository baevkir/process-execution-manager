package com.pem.logic;

import com.pem.core.context.OperationContext;
import com.pem.core.context.AbstractOperationContextWrapper;
import com.pem.core.context.OperationContextImpl;

import java.math.BigDecimal;

public class MathOperationContext extends AbstractOperationContextWrapper {
    public static final String FIRST_PARAM = "firstParam";
    public static final String SECOND_PARAM = "secondParam";
    public static final String RESULT_PARAM = "result";

    public MathOperationContext(OperationContext context) {
        super(context);
    }

    public MathOperationContext() {
        super(new OperationContextImpl());
    }

    public BigDecimal getFirstParam() {
        return getContextParam(FIRST_PARAM, BigDecimal.class);
    }

    public void setFirstParam(BigDecimal firstParam) {
        setContextParam(FIRST_PARAM, firstParam);
    }

    public BigDecimal getSecondParam() {
        return getContextParam(SECOND_PARAM, BigDecimal.class);
    }

    public void setSecondParam(BigDecimal secondParam) {
        setContextParam(SECOND_PARAM, secondParam);
    }

    public BigDecimal getResult() {
        return getContextParam(RESULT_PARAM, BigDecimal.class);
    }

    public void setResult(BigDecimal result) {
        setContextParam(RESULT_PARAM, result);
    }
}
