package com.pem.logic.service.operation.impl;

import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationObject;
import com.pem.persistence.api.manager.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class OperationServiceImpl implements OperationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationServiceImpl.class);

    private PersistenceManager persistenceManager;

    @Override
    public Mono<OperationObject> createOperation(OperationObject operation) {
        LOGGER.debug("Create new Operation: {}.", operation);
        return persistenceManager.create(operation);
    }

    @Override
    public Mono<Void> updateOperation(OperationObject operation) {
        LOGGER.debug("Update Operation: {}.", operation);
        return persistenceManager.update(operation);
    }

    @Override
    public Mono<Void> deleteOperation(BigInteger id) {
        LOGGER.debug("Delete Operation by id: {}.", id);
        return persistenceManager.delete(id, OperationObject.class);
    }

    @Override
    public Mono<OperationObject> getOperation(BigInteger id) {
        LOGGER.debug("Get Operation by id: {}.", id);
        return persistenceManager.getById(id, OperationObject.class);
    }

    @Override
    public Flux<OperationObject> getAllOperations() {
        return persistenceManager.getAll(OperationObject.class);
    }

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }
}
