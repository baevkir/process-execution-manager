package com.pem.persistence.api.service.process;

import com.pem.model.proccess.record.ExecutionRecordDTO;
import com.pem.model.proccess.record.ExecutionRecordPK;

public interface ExecutionRecordPersistenceService {
    ExecutionRecordDTO createExecutionRecord(ExecutionRecordDTO executionRecord);
    void updateExecutionRecord(ExecutionRecordDTO executionRecord);
    ExecutionRecordDTO findExecutionRecordByPk(ExecutionRecordPK pk);
}
