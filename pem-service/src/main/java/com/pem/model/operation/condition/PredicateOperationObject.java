package com.pem.model.operation.condition;

import com.pem.model.predicate.common.PredicateObject;

public class PredicateOperationObject extends ConditionOperationObject<Boolean> {
    private PredicateObject predicate;

    public PredicateObject getPredicate() {
        return predicate;
    }

    public void setPredicate(PredicateObject predicate) {
        this.predicate = predicate;
    }

    @Override
    public String toString() {
        return "PredicateOperationObject{" +
                "predicate=" + predicate +
                "} " + super.toString();
    }
}
