package com.pem.logic.bean.synchronizer.operation;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.event.LaunchEventHandler;
import com.pem.core.common.event.RegisterLaunchEventHandler;
import com.pem.logic.bean.provider.operation.OperationProvider;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.operation.bean.BeanOperationDTO;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RegisterLaunchEventHandler(facade = ServiceConstants.DATABASE_SYNCHRONIZER_FACADE_NAME)
public class BeanOperationDatabaseSynchronizer implements LaunchEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanOperationDatabaseSynchronizer.class);

    private OperationProvider operationProvider;
    private OperationPersistenceService operationPersistenceService;

    @Override
    public void handle() {
        List<BeanOperationDTO> operations = operationPersistenceService
                .getOperationsByType(BeanOperationDTO.class)
                .collectList()
                .block();

        Set<BeanObject> beanObjects = new HashSet<>(operationProvider.getAllOperationBeanObjects());

        for (BeanOperationDTO operation : operations) {
            if(!beanObjects.contains(operation.getBean())) {
                updateStatus(operation, false);
                continue;
            }

            updateStatus(operation, true);
            beanObjects.remove(operation.getBean());
        }

        for (BeanObject beanObject : beanObjects) {
            createOperation(beanObject);
        }
    }

    private void updateStatus(BeanOperationDTO operation, boolean active){
        LOGGER.debug("Update Status for operation: {}.", operation);
        if (active == operation.isActive()) {
            return;
        }
        operation.setActive(active);
        operationPersistenceService.updateOperation(operation).subscribe();
    }

    private void createOperation(BeanObject beanObject){
        LOGGER.debug("Create new operation for {}.", beanObject);
        BeanOperationDTO operation = new BeanOperationDTO();
        operation.setActive(true);
        operation.setName(beanObject.getName());
        operation.setBean(beanObject);

        operationPersistenceService.createOperation(operation).subscribe();
    }

    public void setOperationProvider(OperationProvider operationProvider) {
        this.operationProvider = operationProvider;
    }

    public void setOperationPersistenceService(OperationPersistenceService operationPersistenceService) {
        this.operationPersistenceService = operationPersistenceService;
    }
}
