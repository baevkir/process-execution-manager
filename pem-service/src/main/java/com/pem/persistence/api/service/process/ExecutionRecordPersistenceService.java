package com.pem.persistence.api.service.process;

import com.pem.model.proccess.record.ExecutionRecordDTO;
import com.pem.model.proccess.record.ExecutionRecordPK;
import reactor.core.publisher.Mono;

public interface ExecutionRecordPersistenceService {
    Mono<ExecutionRecordDTO> createExecutionRecord(ExecutionRecordDTO executionRecord);
    Mono<Void> updateExecutionRecord(ExecutionRecordDTO executionRecord);
    Mono<ExecutionRecordDTO> findExecutionRecordByPk(ExecutionRecordPK pk);
}
