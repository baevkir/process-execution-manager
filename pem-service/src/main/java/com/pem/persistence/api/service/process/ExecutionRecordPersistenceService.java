package com.pem.persistence.api.service.process;

import com.pem.persistence.api.model.proccess.record.ExecutionRecord;
import com.pem.persistence.api.model.proccess.record.ExecutionRecordPK;

public interface ExecutionRecordPersistenceService {
    ExecutionRecord createExecutionRecord(ExecutionRecord executionRecord);
    void updateExecutionRecord(ExecutionRecord executionRecord);
    ExecutionRecord findExecutionRecordByPk(ExecutionRecordPK pk);
}
