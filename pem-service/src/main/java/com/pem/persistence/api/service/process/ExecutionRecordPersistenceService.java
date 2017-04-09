package com.pem.persistence.api.service.process;

import com.pem.model.proccess.record.ExecutionRecordObject;
import com.pem.model.proccess.record.ExecutionRecordPK;
import com.pem.persistence.api.service.common.CreateService;
import com.pem.persistence.api.service.common.PersistenceService;
import com.pem.persistence.api.service.common.UpdateService;
import reactor.core.publisher.Mono;

public interface ExecutionRecordPersistenceService  extends
        PersistenceService<ExecutionRecordObject>,
        CreateService<ExecutionRecordObject>,
        UpdateService<ExecutionRecordObject> {

    Mono<ExecutionRecordObject> findByPk(ExecutionRecordPK pk);
}
