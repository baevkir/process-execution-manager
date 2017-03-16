package com.pem.persistence.mongo.service.process;

import com.pem.model.proccess.record.ExecutionRecordDTO;
import com.pem.model.proccess.record.ExecutionRecordPK;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import com.pem.persistence.mongo.model.proccess.record.ExecutionRecordEntity;
import com.pem.persistence.mongo.repository.process.ExecutionRecordRepository;
import com.pem.persistence.mongo.service.common.AbstractMongoPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class MongoExecutionRecordPersistenceService extends AbstractMongoPersistenceService<ExecutionRecordDTO, ExecutionRecordEntity> implements ExecutionRecordPersistenceService {

    @Autowired
    private ExecutionRecordRepository recordRepository;

    @Override
    protected ExecutionRecordRepository getRepository() {
        return recordRepository;
    }

    @Override
    public Mono<ExecutionRecordDTO> createExecutionRecord(ExecutionRecordDTO executionRecord) {
        return create(executionRecord);
    }

    @Override
    public Mono<Void> updateExecutionRecord(ExecutionRecordDTO executionRecord) {
        return update(executionRecord);
    }

    @Override
    public Mono<ExecutionRecordDTO> findExecutionRecordByPk(ExecutionRecordPK pk) {
        return Mono.just(pk)
                .map(currentPk -> getRepository().findExecutionRecordByPk(pk))
                .map(entity -> convertToObject(entity));
    }

}
