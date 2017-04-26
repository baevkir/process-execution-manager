package com.pem.logic.bean.synchronizer;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.event.LaunchEventHandler;
import com.pem.core.common.event.RegisterLaunchEventHandler;
import com.pem.logic.bean.provider.trigger.TriggerProvider;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.predicate.bean.BeanPredicateObject;
import com.pem.model.trigger.bean.BeanTriggerObject;
import com.pem.model.trigger.common.TriggerObject;
import com.pem.persistence.api.manager.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@RegisterLaunchEventHandler(facade = ServiceConstants.DATABASE_SYNCHRONIZER_FACADE_NAME)
public class TriggerDatabaseSynchronizer  extends AbstractBeanDatabaseSynchronizer<BeanTriggerObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerDatabaseSynchronizer.class);

    private TriggerProvider triggerProvider;

    @Override
    public void handle() {
        Flux<BeanObject> beanObjects = Flux.fromIterable(triggerProvider.getAllTriggerBeanObjects());
        BeanObjectMapper<BeanTriggerObject> mapper = getMapper(getPersistenceManager().getAllByType(BeanTriggerObject.class), beanObjects);

        mapper.getForActivate()
                .flatMap(operation -> updateStatus(operation, true))
                .subscribe();

        mapper.getForDeactivate()
                .flatMap(operation -> updateStatus(operation, false))
                .subscribe();

        mapper.getForCreate()
                .flatMap(beanObject -> createTrigger(beanObject))
                .subscribe();
    }

    private Mono<Void> createTrigger(BeanObject beanObject) {
        LOGGER.debug("Create new trigger for {}.", beanObject);
        BeanTriggerObject triggerObject = new BeanTriggerObject();
        triggerObject.setActive(true);
        triggerObject.setName(beanObject.getName());
        triggerObject.setBean(beanObject);

        return getPersistenceManager().create(triggerObject).then();
    }

    public void setTriggerProvider(TriggerProvider triggerProvider) {
        this.triggerProvider = triggerProvider;
    }

}
