package com.pem.logic;

import com.pem.operation.basic.util.AnnotationOperation;
import com.pem.operation.basic.util.reflection.annotions.OperationMethod;
import com.pem.operation.basic.util.reflection.annotions.Param;

import java.math.BigDecimal;

import static com.pem.logic.MathOperationContext.FIRST_PARAM;
import static com.pem.logic.MathOperationContext.RESULT_PARAM;

public class PutResultToFirstParam extends AnnotationOperation {

    @OperationMethod(result = FIRST_PARAM)
    public BigDecimal executeOperation(@Param(value = RESULT_PARAM, mandatory = true) BigDecimal result) {
        return result;
    }
}
