package com.pem.logic.service.operation.impl;

import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationDTO;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class OperationServiceImpl implements OperationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationServiceImpl.class);

    private OperationPersistenceService persistenceService;

    @Override
    public Mono<OperationDTO> createOperation(OperationDTO operation) {
        LOGGER.debug("Create new Operation: {}.", operation);
        return persistenceService.createOperation(operation);
    }

    @Override
    public Mono<Void> updateOperation(OperationDTO operation) {
        LOGGER.debug("Update Operation: {}.", operation);
        return persistenceService.updateOperation(operation);
    }

    @Override
    public Mono<Void> deleteOperation(BigInteger id) {
        LOGGER.debug("Delete Operation by id: {}.", id);
        return persistenceService.deleteOperation(id);
    }

    @Override
    public Mono<OperationDTO> getOperation(BigInteger id) {
        LOGGER.debug("Get Operation by id: {}.", id);
        return persistenceService.getOperation(id);
    }

    @Override
    public Flux<OperationDTO> getAllOperations() {
        return persistenceService.getAllOperations();
    }

    public void setPersistenceService(OperationPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }
}
