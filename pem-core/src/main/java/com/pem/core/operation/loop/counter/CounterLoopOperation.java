package com.pem.core.operation.loop.counter;

import com.pem.core.operation.basic.Operation;

public interface CounterLoopOperation extends Operation {
    void setCount(int count);
    void setOperation(Operation operation);
}
