package com.pem.logic;

import com.pem.operation.basic.util.AnnotationOperation;
import com.pem.operation.basic.util.reflection.annotions.OperationMethod;
import com.pem.operation.basic.util.reflection.annotions.Param;

import java.math.BigDecimal;

import static com.pem.logic.MathOperationContext.*;

public class SumOperation extends AnnotationOperation {

    @OperationMethod(result = RESULT_PARAM)
    public BigDecimal executeOperation(@Param(value = FIRST_PARAM, mandatory = true) BigDecimal first,
                                        @Param(value = SECOND_PARAM, mandatory = true) BigDecimal second) {
        return first.add(second);
    }
}