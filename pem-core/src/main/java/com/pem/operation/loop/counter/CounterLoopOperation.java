package com.pem.operation.loop.counter;

import com.pem.operation.basic.Operation;

public interface CounterLoopOperation extends Operation {
    void setCount(int count);
    void setOperation(Operation operation);
}
