package com.pem.test.common;

import com.pem.logic.bean.provider.operation.impl.RegisterGlobalOperation;
import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.AbstractOperation;
import com.pem.core.operation.basic.Operation;
import org.springframework.context.annotation.Scope;


@RegisterGlobalOperation("Test global operation.")
@Scope(scopeName = "prototype")
public class GlobalOperation extends AbstractOperation implements Operation {

    @Override
    public void execute(OperationContext context) {

    }
}
