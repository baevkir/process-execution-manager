package com.pem.persistence.service.operation;

import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.repository.operation.OperationRepository;
import com.pem.persistence.service.common.AbstractPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;

public class OperationPersistenceServiceImpl extends AbstractPersistenceService<OperationEntity, OperationRepository> implements OperationPersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPersistenceService.class);

    @Autowired
    private OperationRepository repository;

    @Override
    protected OperationRepository getRepository() {
        return repository;
    }

    @Override
    public OperationEntity createOperation(OperationEntity operationEntity) {
        return create(operationEntity);
    }

    @Override
    public void updateOperation(OperationEntity operationEntity) {
        update(operationEntity);
    }

    @Override
    public OperationEntity getOperation(BigInteger id) {
        return getOne(id);
    }

    @Override
    public List<OperationEntity> getAllOperations() {
        return getAll();
    }

    @Override
    public void deleteOperation(BigInteger id) {
        delete(id);
    }
}
