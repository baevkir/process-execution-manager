package com.pem.persistence.api.service.operation;

import com.pem.model.operation.common.OperationObject;
import com.pem.persistence.api.service.common.CrudService;
import com.pem.persistence.api.service.common.PersistenceService;

public interface OperationPersistenceService extends PersistenceService<OperationObject>, CrudService<OperationObject> {
}
