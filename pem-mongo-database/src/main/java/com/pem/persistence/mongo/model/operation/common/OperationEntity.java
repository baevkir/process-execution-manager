package com.pem.persistence.mongo.model.operation.common;

import com.pem.persistence.mongo.model.common.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pem-operations")
public abstract class OperationEntity extends BaseEntity {

    @Override
    public String toString() {
        return "OperationEntity{} " + super.toString();
    }
}
