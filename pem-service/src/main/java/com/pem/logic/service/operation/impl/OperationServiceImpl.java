package com.pem.logic.service.operation.impl;

import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationDTO;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.math.BigInteger;

public class OperationServiceImpl implements OperationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationServiceImpl.class);

    private OperationPersistenceService persistenceService;

    @Override
    public Observable<OperationDTO> createOperation(final OperationDTO operation) {
        LOGGER.debug("Create new Operation: {}.", operation);
        return Observable.just(persistenceService.createOperation(operation));
    }

    @Override
    public Observable<OperationDTO> updateOperation(OperationDTO operationEntity) {
        LOGGER.debug("Update Operation: {}.", operationEntity);
        persistenceService.updateOperation(operationEntity);
        return Observable.empty();
    }

    @Override
    public Observable<OperationDTO> deleteOperation(BigInteger id) {
        LOGGER.debug("Delete Operation by id: {}.", id);
        persistenceService.deleteOperation(id);
        return Observable.empty();
    }

    @Override
    public Observable<OperationDTO> getOperation(final BigInteger id) {
        LOGGER.debug("Get Operation by id: {}.", id);
        return Observable.just(persistenceService.getOperation(id));
    }

    @Override
    public Observable<OperationDTO> getAllOperations() {
        return Observable.from(persistenceService.getAllOperations());
    }

    public void setPersistenceService(OperationPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }
}
