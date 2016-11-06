package com.pem.operation.composite;

import com.pem.operation.basic.Operation;

public interface CompositeOperation extends Operation {
    void addOperation(Operation operation);
}
