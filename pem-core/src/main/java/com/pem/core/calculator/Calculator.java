package com.pem.core.calculator;

import com.pem.core.common.Identifiable;
import com.pem.core.context.OperationContext;


public interface Calculator<C> extends Identifiable {
    C calculate(OperationContext context);
}
