package com.pem.core.operation.basic;

import com.pem.core.context.OperationContext;

public interface Operation {
    String getOperationId();
    void setOperationId(String id);
    void execute(OperationContext context);
}
