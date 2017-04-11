package com.pem.core.operation.basic.util.reflection;

import com.pem.core.context.OperationContext;

public interface AnnotationOperationInvoker {
    OperationContext invoke(OperationContext context);
}
