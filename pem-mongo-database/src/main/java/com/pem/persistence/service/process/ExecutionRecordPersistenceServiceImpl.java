package com.pem.persistence.service.process;

import com.pem.persistence.model.proccess.record.ExecutionRecordEntity;
import com.pem.persistence.model.proccess.record.ExecutionRecordPK;
import com.pem.persistence.repository.process.ExecutionRecordRepository;
import com.pem.persistence.service.common.AbstractPersistenceService;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;

public class ExecutionRecordPersistenceServiceImpl  extends AbstractPersistenceService<ExecutionRecordEntity, ExecutionRecordRepository> implements ExecutionRecordPersistenceService {

    @Autowired
    private ExecutionRecordRepository recordRepository;

    @Override
    protected ExecutionRecordRepository getRepository() {
        return recordRepository;
    }

    @Override
    public ExecutionRecordEntity createExecutionRecord(ExecutionRecordEntity executionRecord) {
        return create(executionRecord);
    }

    @Override
    public void updateExecutionRecord(ExecutionRecordEntity executionRecord) {
        update(executionRecord);
    }

    @Override
    public ExecutionRecordEntity findExecutionRecordByPk(ExecutionRecordPK pk) {
        return getRepository().findExecutionRecordByPk(pk);
    }

}
