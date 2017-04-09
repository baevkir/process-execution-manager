package com.pem.persistence.mongo.service.operation;

import com.pem.model.operation.common.OperationObject;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.repository.operation.OperationRepository;
import com.pem.persistence.mongo.service.common.AbstractMongoPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class MongoOperationPersistenceService extends AbstractMongoPersistenceService<OperationObject, OperationEntity> implements OperationPersistenceService {

    private OperationRepository repository;

    @Override
    protected OperationRepository getRepository() {
        return repository;
    }

    @Override
    public Mono<OperationObject> create(OperationObject operationObject) {
        return internalCreate(operationObject);
    }

    @Override
    public Mono<Void> update(OperationObject operationEntity) {
        return internalUpdate(operationEntity);
    }

    @Override
    public Mono<OperationObject> getById(BigInteger id) {
        return internalGetById(id);
    }

    @Override
    public Flux<OperationObject> getAll() {
        return internalGetAll();
    }

    @Override
    public <O extends OperationObject> Flux<O> getAllByType(final Class<O> targetClass) {
        return internalGetAllByType(targetClass);
    }

    @Override
    public Mono<Void> delete(BigInteger id) {
        return internalDelete(id);
    }

    @Autowired
    public void setRepository(OperationRepository repository) {
        this.repository = repository;
    }
}
