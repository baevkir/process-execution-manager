package com.pem.operation.basic;

import com.pem.context.OperationContext;

public interface Operation {
    String getOperationId();
    void setOperationId(String id);
    void execute(OperationContext context);
}
