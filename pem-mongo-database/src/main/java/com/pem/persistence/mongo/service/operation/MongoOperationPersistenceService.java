package com.pem.persistence.mongo.service.operation;

import com.pem.model.operation.common.OperationDTO;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.repository.operation.OperationRepository;
import com.pem.persistence.mongo.service.common.AbstractMongoPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class MongoOperationPersistenceService extends AbstractMongoPersistenceService<OperationDTO, OperationEntity> implements OperationPersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMongoPersistenceService.class);

    @Autowired
    private OperationRepository repository;

    @Override
    protected OperationRepository getRepository() {
        return repository;
    }

    @Override
    public Mono<OperationDTO> createOperation(OperationDTO operationObject) {
        return create(operationObject);
    }

    @Override
    public Mono<Void> updateOperation(OperationDTO operationEntity) {
        return update(operationEntity);
    }

    @Override
    public Mono<OperationDTO> getOperation(BigInteger id) {
        return getOne(id);
    }

    @Override
    public Flux<OperationDTO> getAllOperations() {
        return getAll();
    }

    @Override
    public <O extends OperationDTO> Flux<O> getOperationsByType(final Class<O> targetClass) {
        return getAllByType(targetClass);
    }

    @Override
    public Mono<Void> deleteOperation(BigInteger id) {
        return delete(id);
    }
}
