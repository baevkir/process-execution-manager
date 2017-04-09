package com.pem.logic.bean.synchronizer;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.event.LaunchEventHandler;
import com.pem.core.common.event.RegisterLaunchEventHandler;
import com.pem.logic.bean.provider.trigger.TriggerProvider;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.trigger.bean.BeanTriggerObject;
import com.pem.model.trigger.common.TriggerObject;
import com.pem.persistence.api.manager.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@RegisterLaunchEventHandler(facade = ServiceConstants.DATABASE_SYNCHRONIZER_FACADE_NAME)
public class TriggerDatabaseSynchronizer implements LaunchEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerDatabaseSynchronizer.class);

    private TriggerProvider triggerProvider;
    private PersistenceManager persistenceManager;

    @Override
    public void handle() {
        Set<BeanObject> beanObjects = new HashSet<>(triggerProvider.getAllTriggerBeanObjects());

        persistenceManager.getAllByType(BeanTriggerObject.class)
                .doOnError(Exception.class, exception -> {
                    throw new RuntimeException(exception);
                })
                .subscribe(triggerObject -> {
                    if (!beanObjects.contains(triggerObject.getBean())) {
                        updateStatus(triggerObject, false);
                        return;
                    }

                    updateStatus(triggerObject, true);
                    beanObjects.remove(triggerObject.getBean());
                });

        beanObjects.forEach(beanObject -> createTrigger(beanObject));
    }

    private void updateStatus(TriggerObject triggerObject, boolean active) {
        LOGGER.debug("Update Status for trigger: {}.", triggerObject);
        if (active == triggerObject.isActive()) {
            return;
        }
        triggerObject.setActive(active);
        persistenceManager.update(triggerObject)
                .doOnError(Exception.class, exception -> {
                    throw new RuntimeException(exception);
                })
                .subscribe();
    }

    private void createTrigger(BeanObject beanObject) {
        LOGGER.debug("Create new trigger for {}.", beanObject);
        BeanTriggerObject triggerObject = new BeanTriggerObject();
        triggerObject.setActive(true);
        triggerObject.setName(beanObject.getName());
        triggerObject.setBean(beanObject);

        persistenceManager.create(triggerObject)
                .doOnError(Exception.class, exception -> {
                    throw new RuntimeException(exception);
                })
                .subscribe();
    }

    public void setTriggerProvider(TriggerProvider triggerProvider) {
        this.triggerProvider = triggerProvider;
    }

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }
}
