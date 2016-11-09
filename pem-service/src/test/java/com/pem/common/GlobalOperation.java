package com.pem.common;

import com.pem.context.OperationContext;
import com.pem.operation.basic.Operation;
import com.pem.common.provider.annotatin.RegisterGlobalOperation;

@RegisterGlobalOperation("Test global operation.")
public class GlobalOperation implements Operation {

    @Override
    public void execute(OperationContext context) {

    }
}
