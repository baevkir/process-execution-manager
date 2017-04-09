package com.pem.persistence.mongo.model.predicate.common;

import com.pem.persistence.mongo.model.common.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pem_predicates")
public abstract class PredicateEntity extends BaseEntity {
    @Override
    public String toString() {
        return "PredicateEntity{} " + super.toString();
    }
}
