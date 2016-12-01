package com.pem.persistence.api.service.process;

import com.pem.model.proccess.record.ExecutionRecord;
import com.pem.model.proccess.record.ExecutionRecordPK;

public interface ExecutionRecordPersistenceService {
    ExecutionRecord createExecutionRecord(ExecutionRecord executionRecord);
    void updateExecutionRecord(ExecutionRecord executionRecord);
    ExecutionRecord findExecutionRecordByPk(ExecutionRecordPK pk);
}
