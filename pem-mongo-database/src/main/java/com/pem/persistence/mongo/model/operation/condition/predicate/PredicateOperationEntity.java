package com.pem.persistence.mongo.model.operation.condition.predicate;

import com.pem.persistence.mongo.model.operation.condition.ConditionOperationEntity;
import com.pem.persistence.mongo.model.operation.condition.state.BooleanState;
import com.pem.persistence.mongo.model.predicate.common.PredicateEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class PredicateOperationEntity extends ConditionOperationEntity<BooleanState> {
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
        return "PredicateOperationEntity{" +
                "predicate=" + predicate +
                "} " + super.toString();
    }
}
