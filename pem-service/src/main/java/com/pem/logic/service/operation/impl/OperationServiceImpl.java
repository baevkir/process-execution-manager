package com.pem.logic.service.operation.impl;

import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationDTO;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class OperationServiceImpl implements OperationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationServiceImpl.class);

    private OperationPersistenceService persistenceService;

    @Override
    public Single<OperationDTO> createOperation(final OperationDTO operation) {
        LOGGER.debug("Create new Operation: {}.", operation);
        return Single.fromCallable(() -> persistenceService.createOperation(operation));
    }

    @Override
    public Completable updateOperation(OperationDTO operationEntity) {
        LOGGER.debug("Update Operation: {}.", operationEntity);
        return Completable.fromAction(() -> persistenceService.updateOperation(operationEntity));
    }

    @Override
    public Completable deleteOperation(BigInteger id) {
        LOGGER.debug("Delete Operation by id: {}.", id);
        return Completable.fromAction(() ->  persistenceService.deleteOperation(id));
    }

    @Override
    public Single<OperationDTO> getOperation(final BigInteger id) {
        LOGGER.debug("Get Operation by id: {}.", id);
        return Single.fromCallable(() -> persistenceService.getOperation(id));
    }

    @Override
    public Observable<OperationDTO> getAllOperations() {
        return Observable.fromIterable(persistenceService.getAllOperations());
    }

    public void setPersistenceService(OperationPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }
}
