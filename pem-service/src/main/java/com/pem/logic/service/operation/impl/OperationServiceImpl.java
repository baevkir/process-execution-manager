package com.pem.logic.service.operation.impl;

import com.pem.logic.service.operation.OperationService;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.model.operation.common.OperationEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;

public class OperationServiceImpl implements OperationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationServiceImpl.class);

    private OperationPersistenceService persistenceService;

    @Override
    public OperationEntity createOperation(OperationEntity operationEntity) {
        LOGGER.debug("Create new Operation: {}.", operationEntity);
        return persistenceService.createOperation(operationEntity);
    }

    @Override
    public void updateOperation(OperationEntity operationEntity) {
        LOGGER.debug("Update Operation: {}.", operationEntity);
        persistenceService.updateOperation(operationEntity);
    }

    @Override
    public void deleteOperation(BigInteger id) {
        LOGGER.debug("Delete Operation by id: {}.", id);
        persistenceService.deleteOperation(id);
    }

    @Override
    public OperationEntity getOperation(BigInteger id) {
        LOGGER.debug("Get Operation by id: {}.", id);
        return persistenceService.getOperation(id);
    }

    @Override
    public List<OperationEntity> getAllOperations() {
        return persistenceService.getAllOperations();
    }

    @Override
    public void runOperation(BigInteger id) {

    }

    public void setPersistenceService(OperationPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }
}
