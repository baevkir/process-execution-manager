package com.pem.persistence.service.process;

import com.pem.persistence.model.proccess.record.ExecutionRecordEntity;
import com.pem.persistence.model.proccess.record.ExecutionRecordPK;

public interface ExecutionRecordPersistenceService {
    ExecutionRecordEntity createExecutionRecord(ExecutionRecordEntity executionRecord);
    void updateExecutionRecord(ExecutionRecordEntity executionRecord);
    ExecutionRecordEntity findExecutionRecordByPk(ExecutionRecordPK pk);
}
