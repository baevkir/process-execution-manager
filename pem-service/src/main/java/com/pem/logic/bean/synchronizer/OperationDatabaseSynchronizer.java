package com.pem.logic.bean.synchronizer;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.event.LaunchEventHandler;
import com.pem.core.common.event.RegisterLaunchEventHandler;
import com.pem.logic.bean.provider.operation.OperationProvider;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.operation.bean.BeanOperationObject;
import com.pem.persistence.api.manager.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@RegisterLaunchEventHandler(facade = ServiceConstants.DATABASE_SYNCHRONIZER_FACADE_NAME)
public class OperationDatabaseSynchronizer implements LaunchEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationDatabaseSynchronizer.class);

    private OperationProvider operationProvider;
    private PersistenceManager persistenceManager;

    @Override
    public void handle() {
        Set<BeanObject> beanObjects = new HashSet<>(operationProvider.getAllOperationBeanObjects());
        persistenceManager.getAllByType(BeanOperationObject.class)
                .doOnError(Exception.class, exception -> {
                    throw new RuntimeException(exception);
                })
                .subscribe(operation -> {
                    if (!beanObjects.contains(operation.getBean())) {
                        updateStatus(operation, false);
                        return;
                    }

                    updateStatus(operation, true);
                    beanObjects.remove(operation.getBean());
                });

        beanObjects.forEach(beanObject -> createOperation(beanObject));
    }

    private void updateStatus(BeanOperationObject operation, boolean active) {
        LOGGER.debug("Update Status for operation: {}.", operation);
        if (active == operation.isActive()) {
            return;
        }
        operation.setActive(active);
        persistenceManager.update(operation)
                .doOnError(Exception.class, exception -> {
                    throw new RuntimeException(exception);
                })
                .subscribe();
    }

    private void createOperation(BeanObject beanObject) {
        LOGGER.debug("Create new operation for {}.", beanObject);
        BeanOperationObject operation = new BeanOperationObject();
        operation.setActive(true);
        operation.setName(beanObject.getName());
        operation.setBean(beanObject);

        persistenceManager.create(operation)
                .doOnError(Exception.class, exception -> {
                    throw new RuntimeException(exception);
                })
                .subscribe();
    }

    public void setOperationProvider(OperationProvider operationProvider) {
        this.operationProvider = operationProvider;
    }

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }
}
