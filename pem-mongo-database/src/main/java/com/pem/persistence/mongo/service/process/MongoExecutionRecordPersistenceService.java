package com.pem.persistence.mongo.service.process;

import com.pem.model.proccess.record.ExecutionRecordObject;
import com.pem.model.proccess.record.ExecutionRecordPK;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import com.pem.persistence.mongo.model.proccess.record.ExecutionRecordEntity;
import com.pem.persistence.mongo.repository.process.ExecutionRecordRepository;
import com.pem.persistence.mongo.service.common.AbstractMongoPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class MongoExecutionRecordPersistenceService extends AbstractMongoPersistenceService<ExecutionRecordObject, ExecutionRecordEntity> implements ExecutionRecordPersistenceService {

    private ExecutionRecordRepository recordRepository;

    @Override
    protected ExecutionRecordRepository getRepository() {
        return recordRepository;
    }

    @Override
    public Mono<ExecutionRecordObject> create(ExecutionRecordObject executionRecord) {
        return internalCreate(executionRecord);
    }

    @Override
    public Mono<Void> update(ExecutionRecordObject executionRecord) {
        return internalUpdate(executionRecord);
    }

    @Override
    public Mono<ExecutionRecordObject> findByPk(ExecutionRecordPK pk) {
        return Mono.just(pk)
                .map(currentPk -> getRepository().findExecutionRecordByPk(pk))
                .map(entity -> convertToObject(entity));
    }

    @Autowired
    public void setRecordRepository(ExecutionRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }
}
