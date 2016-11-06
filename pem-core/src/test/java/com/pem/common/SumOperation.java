package com.pem.common;

import com.pem.operation.basic.AnnotationOperation;
import com.pem.operation.basic.reflection.annotions.OperationMethod;
import com.pem.operation.basic.reflection.annotions.Param;

import java.math.BigDecimal;

import static com.pem.common.MathOperationContext.*;

public class SumOperation extends AnnotationOperation {

    @OperationMethod(result = RESULT_PARAM)
    public BigDecimal executeOperation(@Param(value = FIRST_PARAM, mandatory = true) BigDecimal first,
                                        @Param(value = SECOND_PARAM, mandatory = true) BigDecimal second) {
        return first.add(second);
    }
}