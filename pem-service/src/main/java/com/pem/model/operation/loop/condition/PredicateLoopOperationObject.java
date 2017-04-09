package com.pem.model.operation.loop.condition;

import com.pem.model.operation.loop.LoopOperationObject;
import com.pem.model.predicate.common.PredicateObject;

public abstract class PredicateLoopOperationObject extends LoopOperationObject {

    private PredicateObject predicate;

    public PredicateObject getPredicate() {
        return predicate;
    }

    public void setPredicate(PredicateObject predicate) {
        this.predicate = predicate;
    }

    @Override
    public String toString() {
        return "PredicateLoopOperationObject{" +
                "trigger=" + predicate +
                "} " + super.toString();
    }
}
