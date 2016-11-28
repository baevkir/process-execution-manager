package com.pem.logic;

import com.pem.operation.basic.util.AnnotationOperation;
import com.pem.operation.basic.util.reflection.annotions.OperationMethod;
import com.pem.operation.basic.util.reflection.annotions.Param;

import java.math.BigDecimal;

import static com.pem.logic.MathOperationContext.FIRST_PARAM;
import static com.pem.logic.MathOperationContext.RESULT_PARAM;
import static com.pem.logic.MathOperationContext.SECOND_PARAM;

public class SubtractOperation extends AnnotationOperation {

    @OperationMethod(result = RESULT_PARAM)
    public BigDecimal executeOperation(@Param(value = FIRST_PARAM, mandatory = true) BigDecimal first,
                                        @Param(value = SECOND_PARAM, mandatory = true) BigDecimal second) {
        return first.subtract(second);
    }
}
