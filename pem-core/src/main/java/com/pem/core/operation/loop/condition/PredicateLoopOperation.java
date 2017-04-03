package com.pem.core.operation.loop.condition;

import com.pem.core.operation.basic.Operation;
import com.pem.core.predicate.Predicate;

public interface PredicateLoopOperation extends Operation {
    void setPredicate(Predicate predicate);
    void setOperation(Operation operation);
}
