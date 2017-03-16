package com.pem.persistence.mongo.service.process;

import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import com.pem.persistence.mongo.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.mongo.repository.process.ProcessRepository;
import com.pem.persistence.mongo.service.common.AbstractMongoPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class MongoProcessPersistenceService extends AbstractMongoPersistenceService<ExecutionProcessDTO, ExecutionProcessEntity> implements ProcessPersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMongoPersistenceService.class);

    @Autowired
    private ProcessRepository repository;

    @Override
    protected ProcessRepository getRepository() {
        return repository;
    }

    @Override
    public Mono<ExecutionProcessDTO> createProcess(ExecutionProcessDTO processEntity) {
        return create(processEntity);
    }

    @Override
    public Mono<Void> updateProcess(ExecutionProcessDTO processEntity) {
        return update(processEntity);
    }

    @Override
    public Mono<ExecutionProcessDTO> getProcess(BigInteger id) {
        return getOne(id);
    }

    @Override
    public Flux<ExecutionProcessDTO> getAllProcesses() {
        return getAll();
    }
}
