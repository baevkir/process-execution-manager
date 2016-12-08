package com.pem.logic.service.operation.impl;

import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationDTO;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;

public class OperationServiceImpl implements OperationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationServiceImpl.class);

    private OperationPersistenceService persistenceService;

    @Override
    public OperationDTO createOperation(OperationDTO operationEntity) {
        LOGGER.debug("Create new Operation: {}.", operationEntity);
        return persistenceService.createOperation(operationEntity);
    }

    @Override
    public void updateOperation(OperationDTO operationEntity) {
        LOGGER.debug("Update Operation: {}.", operationEntity);
        persistenceService.updateOperation(operationEntity);
    }

    @Override
    public void deleteOperation(BigInteger id) {
        LOGGER.debug("Delete Operation by id: {}.", id);
        persistenceService.deleteOperation(id);
    }

    @Override
    public OperationDTO getOperation(BigInteger id) {
        LOGGER.debug("Get Operation by id: {}.", id);
        return persistenceService.getOperation(id);
    }

    @Override
    public List<OperationDTO> getAllOperations() {
        return persistenceService.getAllOperations();
    }

    public void setPersistenceService(OperationPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }
}
