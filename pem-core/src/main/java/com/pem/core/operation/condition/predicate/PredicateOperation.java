package com.pem.core.operation.condition.predicate;

import com.pem.core.operation.condition.ConditionOperation;

import com.pem.core.predicate.Predicate;

public interface PredicateOperation extends ConditionOperation<Boolean> {
    void setPredicate(Predicate predicate);
}
