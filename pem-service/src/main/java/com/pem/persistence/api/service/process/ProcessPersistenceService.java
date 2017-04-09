package com.pem.persistence.api.service.process;

import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.persistence.api.service.common.*;

public interface ProcessPersistenceService extends
        PersistenceService<ExecutionProcessObject>,
        CreateService<ExecutionProcessObject>,
        UpdateService<ExecutionProcessObject>,
        GetByIdService<ExecutionProcessObject>,
        GetAllService<ExecutionProcessObject> {

}
