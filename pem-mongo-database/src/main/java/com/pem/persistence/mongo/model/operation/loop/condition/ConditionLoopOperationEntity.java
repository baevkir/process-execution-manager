package com.pem.persistence.mongo.model.operation.loop.condition;

import com.pem.persistence.mongo.model.operation.loop.LoopOperationEntity;
import com.pem.persistence.mongo.model.predicate.common.PredicateEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class ConditionLoopOperationEntity extends LoopOperationEntity {

    @DBRef
    private PredicateEntity predicate;

    public PredicateEntity getPredicate() {
        return predicate;
    }

    public void setPredicate(PredicateEntity predicate) {
        this.predicate = predicate;
    }

    @Override
    public String toString() {
        return "ConditionLoopOperationEntity{" +
                "predicate=" + predicate +
                "} " + super.toString();
    }
}
