package com.pem.database.model.operation.common;

import com.pem.database.model.common.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "operations")
public abstract class OperationEntity extends BaseEntity{

    @Override
    public String toString() {
        return "OperationEntity{} " + super.toString();
    }
}
