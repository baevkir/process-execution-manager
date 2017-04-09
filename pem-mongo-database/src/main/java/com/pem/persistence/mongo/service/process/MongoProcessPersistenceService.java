package com.pem.persistence.mongo.service.process;

import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import com.pem.persistence.mongo.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.mongo.repository.process.ProcessRepository;
import com.pem.persistence.mongo.service.common.AbstractMongoPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class MongoProcessPersistenceService extends AbstractMongoPersistenceService<ExecutionProcessObject, ExecutionProcessEntity> implements ProcessPersistenceService {
    private ProcessRepository repository;

    @Override
    protected ProcessRepository getRepository() {
        return repository;
    }

    @Override
    public Mono<ExecutionProcessObject> create(ExecutionProcessObject processEntity) {
        return internalCreate(processEntity);
    }

    @Override
    public Mono<Void> update(ExecutionProcessObject processEntity) {
        return internalUpdate(processEntity);
    }

    @Override
    public Mono<ExecutionProcessObject> getById(BigInteger id) {
        return internalGetById(id);
    }

    @Override
    public Flux<ExecutionProcessObject> getAll() {
        return internalGetAll();
    }

    @Autowired
    public void setRepository(ProcessRepository repository) {
        this.repository = repository;
    }
}
