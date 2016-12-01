package com.pem.persistence.mongo.model.proccess.record;

public enum ExecutionRecordState {
    CREATED,
    IN_PROGRESS,
    COMPLETED,
    ERROR,
    CANCELED
}
