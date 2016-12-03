package com.pem.logic;

import com.pem.core.operation.basic.util.AnnotationOperation;
import com.pem.core.operation.basic.util.reflection.annotions.OperationMethod;
import com.pem.core.operation.basic.util.reflection.annotions.Param;

import java.math.BigDecimal;

import static com.pem.logic.MathOperationContext.RESULT_PARAM;
import static com.pem.logic.MathOperationContext.SECOND_PARAM;

public class PutResultToSecondParam extends AnnotationOperation {

    @OperationMethod(result = SECOND_PARAM)
    public BigDecimal executeOperation(@Param(value = RESULT_PARAM, mandatory = true) BigDecimal result) {
        return result;
    }
}
