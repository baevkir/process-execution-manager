package com.pem.common;


import com.pem.operation.basic.AnnotationOperation;
import com.pem.operation.basic.reflection.annotions.OperationMethod;
import com.pem.operation.basic.reflection.annotions.Param;

import java.math.BigDecimal;

import static com.pem.common.MathOperationContext.FIRST_PARAM;
import static com.pem.common.MathOperationContext.RESULT_PARAM;


public class MultiplyOperation extends AnnotationOperation {
    @OperationMethod(result = RESULT_PARAM)
    public BigDecimal executeOperation(@Param(value = RESULT_PARAM, mandatory = true) BigDecimal param,
                                       @Param(value = FIRST_PARAM, mandatory = true) BigDecimal multiplicand) {
        return param.multiply(multiplicand);
    }
}
