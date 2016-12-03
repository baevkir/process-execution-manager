package com.pem.core.operation.composite;

import com.pem.core.operation.basic.Operation;

public interface CompositeOperation extends Operation {
    void addOperation(Operation operation);
}
