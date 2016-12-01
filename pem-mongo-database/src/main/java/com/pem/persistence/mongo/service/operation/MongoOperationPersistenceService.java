package com.pem.persistence.mongo.service.operation;

import com.pem.model.operation.common.OperationDTO;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.repository.operation.OperationRepository;
import com.pem.persistence.mongo.service.common.AbstractMongoPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;

public class MongoOperationPersistenceService extends AbstractMongoPersistenceService<OperationDTO, OperationEntity> implements OperationPersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMongoPersistenceService.class);

    @Autowired
    private OperationRepository repository;

    @Override
    protected OperationRepository getRepository() {
        return repository;
    }

    @Override
    public OperationDTO createOperation(OperationDTO operationObject) {
        return create(operationObject);
    }

    @Override
    public void updateOperation(OperationDTO operationEntity) {
        update(operationEntity);
    }

    @Override
    public OperationDTO getOperation(BigInteger id) {
        return getOne(id);
    }

    @Override
    public List<OperationDTO> getAllOperations() {
        return getAll();
    }

    @Override
    public <O extends OperationDTO> List<O> getOperationsByType(final Class<O> targetClass) {
        return getAllByType(targetClass);
    }

    @Override
    public void deleteOperation(BigInteger id) {
        delete(id);
    }
}
