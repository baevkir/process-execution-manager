package com.pem.test.common;

import com.pem.common.provider.annotatin.RegisterGlobalOperation;
import com.pem.context.OperationContext;
import com.pem.operation.basic.Operation;


@RegisterGlobalOperation("Test global operation.")
public class GlobalOperation implements Operation {

    @Override
    public void execute(OperationContext context) {

    }
}
