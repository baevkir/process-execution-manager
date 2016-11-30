package com.pem.persistence.service.operation;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.repository.operation.OperationRepository;
import com.pem.persistence.service.common.AbstractMongoPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.List;

public class MongoOperationPersistenceService extends AbstractMongoPersistenceService<OperationEntity, OperationRepository> implements OperationPersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMongoPersistenceService.class);

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
    public <O extends OperationEntity> List<O> getOperationsByType(final Class<O> targetClass) {
        String className = targetClass.getCanonicalName();
        List<OperationEntity> operationEntities = repository.findByImplementation(className);

        return FluentIterable.from(operationEntities).transform(new Function<OperationEntity, O>() {
            @Override
            public O apply(OperationEntity input) {
                Assert.isInstanceOf(targetClass, input);
                return targetClass.cast(input);
            }
        }).toList();
    }

    @Override
    public void deleteOperation(BigInteger id) {
        delete(id);
    }
}
