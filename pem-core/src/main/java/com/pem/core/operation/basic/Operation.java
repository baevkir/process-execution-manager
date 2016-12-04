package com.pem.core.operation.basic;

import com.pem.core.common.Identifiable;
import com.pem.core.context.OperationContext;

public interface Operation extends Identifiable {
    void execute(OperationContext context);
}
