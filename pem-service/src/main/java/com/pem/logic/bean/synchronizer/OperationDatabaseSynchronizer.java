package com.pem.logic.bean.synchronizer;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.event.RegisterLaunchEventHandler;
import com.pem.logic.bean.provider.operation.OperationProvider;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.operation.bean.BeanOperationObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RegisterLaunchEventHandler(facade = ServiceConstants.DATABASE_SYNCHRONIZER_FACADE_NAME)
public class OperationDatabaseSynchronizer extends AbstractBeanDatabaseSynchronizer<BeanOperationObject> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationDatabaseSynchronizer.class);

    private OperationProvider operationProvider;

    @Override
    public void handle() {
        Flux<BeanObject> beanObjects = Flux.fromIterable(operationProvider.getAllOperationBeanObjects());
        BeanObjectMapper<BeanOperationObject> mapper = getMapper(getPersistenceManager().getAllByType(BeanOperationObject.class), beanObjects);

        mapper.getForActivate()
                .flatMap(operation -> updateStatus(operation, true))
                .subscribe();

        mapper.getForDeactivate()
                .flatMap(operation -> updateStatus(operation, false))
                .subscribe();

        mapper.getForCreate()
                .flatMap(beanObject -> createOperation(beanObject))
                .subscribe();
    }

    private Mono<Void> createOperation(BeanObject beanObject) {
        LOGGER.debug("Create new operation for {}.", beanObject);
        BeanOperationObject operation = new BeanOperationObject();
        operation.setActive(true);
        operation.setName(beanObject.getName());
        operation.setBean(beanObject);

        return getPersistenceManager().create(operation).then();
    }

    public void setOperationProvider(OperationProvider operationProvider) {
        this.operationProvider = operationProvider;
    }
}
