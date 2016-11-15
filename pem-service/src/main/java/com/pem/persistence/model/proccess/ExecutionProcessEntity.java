package com.pem.persistence.model.proccess;

import com.pem.persistence.model.common.BaseEntity;
import com.pem.persistence.model.operation.common.OperationEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "execution-processes")
public class ExecutionProcessEntity extends BaseEntity {

    @DBRef
    private OperationEntity operationEntity;


    public OperationEntity getOperationEntity() {
        return operationEntity;
    }

    public void setOperationEntity(OperationEntity operationEntity) {
        this.operationEntity = operationEntity;
    }

    @Override
    public String toString() {
        return "ExecutionProcessEntity{" +
                "operationEntity=" + operationEntity +
                "} " + super.toString();
    }
}
