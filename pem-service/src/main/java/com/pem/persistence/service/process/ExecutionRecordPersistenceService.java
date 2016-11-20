package com.pem.persistence.service.process;

import com.pem.persistence.model.proccess.record.ExecutionRecordEntity;
import com.pem.persistence.model.proccess.record.ExecutionRecordPK;

public interface ExecutionRecordPersistenceService {
    void updateExecutionRecord(ExecutionRecordEntity executionRecord);
    ExecutionRecordEntity findExecutionRecordByPk(ExecutionRecordPK pk);
}
