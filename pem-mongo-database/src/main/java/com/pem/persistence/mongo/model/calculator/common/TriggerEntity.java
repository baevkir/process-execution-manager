package com.pem.persistence.mongo.model.calculator.common;

import com.pem.persistence.mongo.model.common.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pem_triggers")
public abstract class TriggerEntity extends BaseEntity {

    @Override
    public String toString() {
        return "TriggerEntity{} " + super.toString();
    }
}
