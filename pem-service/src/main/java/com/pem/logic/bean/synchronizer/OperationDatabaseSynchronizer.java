package com.pem.logic.bean.synchronizer;

import com.pem.logic.bean.provider.operation.OperationProvider;
import com.pem.model.common.bean.BeanObject;
import com.pem.model.operation.basic.BeanOperationDTO;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

public class OperationDatabaseSynchronizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationDatabaseSynchronizer.class);

    private OperationProvider operationProvider;
    private OperationPersistenceService operationPersistenceService;

    public void setOperationProvider(OperationProvider operationProvider) {
        this.operationProvider = operationProvider;
    }

    public void setOperationPersistenceService(OperationPersistenceService operationPersistenceService) {
        this.operationPersistenceService = operationPersistenceService;
    }

    @PostConstruct
    public void synchronizeOperationsWithDatabase() {
        List<BeanOperationDTO> operations = operationPersistenceService.getOperationsByType(BeanOperationDTO.class);

        Set<BeanObject> beanObjects = operationProvider.getAllOperationBeanObjects();

        for (BeanOperationDTO operation : operations) {
            if(!beanObjects.contains(operation.getBean())) {
                deactivateOperation(operation);
                continue;
            }

            beanObjects.remove(operation.getBean());
        }

        for (BeanObject beanObject : beanObjects) {
            createOperation(beanObject);
        }
    }

    private void deactivateOperation(BeanOperationDTO operation){
        LOGGER.debug("Deactivate operation: {}.", operation);
        operation.setActive(false);
        operationPersistenceService.updateOperation(operation);
    }

    private void createOperation(BeanObject beanObject){
        LOGGER.debug("Create new operation for {}.", beanObject);
        BeanOperationDTO operation = new BeanOperationDTO();
        operation.setActive(true);
        operation.setName(beanObject.getName());
        operation.setBean(beanObject);

        operationPersistenceService.createOperation(operation);
    }
}
