package com.pem.persistence.mongo.service.process;

import com.pem.model.proccess.record.ExecutionRecordDTO;
import com.pem.model.proccess.record.ExecutionRecordPK;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import com.pem.persistence.mongo.model.proccess.record.ExecutionRecordEntity;
import com.pem.persistence.mongo.repository.process.ExecutionRecordRepository;
import com.pem.persistence.mongo.service.common.AbstractMongoPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;

public class MongoExecutionRecordPersistenceService extends AbstractMongoPersistenceService<ExecutionRecordDTO, ExecutionRecordEntity> implements ExecutionRecordPersistenceService {

    @Autowired
    private ExecutionRecordRepository recordRepository;

    @Override
    protected ExecutionRecordRepository getRepository() {
        return recordRepository;
    }

    @Override
    public ExecutionRecordDTO createExecutionRecord(ExecutionRecordDTO executionRecord) {
        return create(executionRecord);
    }

    @Override
    public void updateExecutionRecord(ExecutionRecordDTO executionRecord) {
        update(executionRecord);
    }

    @Override
    public ExecutionRecordDTO findExecutionRecordByPk(ExecutionRecordPK pk) {
        ExecutionRecordEntity executionRecordEntity = getRepository().findExecutionRecordByPk(pk);
        return convertToObject(executionRecordEntity);
    }

}
