package com.pem.persistence.service.process;

import com.pem.persistence.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.repository.process.ProcessRepository;
import com.pem.persistence.service.common.AbstractPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;

public class ProcessPersistenceServiceImpl extends AbstractPersistenceService<ExecutionProcessEntity, ProcessRepository> implements ProcessPersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPersistenceService.class);

    @Autowired
    private ProcessRepository repository;

    @Override
    protected ProcessRepository getRepository() {
        return repository;
    }

    @Override
    public ExecutionProcessEntity createProcess(ExecutionProcessEntity processEntity) {
        return create(processEntity);
    }

    @Override
    public void updateProcess(ExecutionProcessEntity processEntity) {
        update(processEntity);
    }

    @Override
    public ExecutionProcessEntity getProcess(BigInteger id) {
        return getOne(id);
    }

    @Override
    public List<ExecutionProcessEntity> getAllProcesses() {
        return getAll();
    }

}
