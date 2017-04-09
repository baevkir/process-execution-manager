package com.pem.logic.bean.synchronizer;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.event.LaunchEventHandler;
import com.pem.core.common.event.RegisterLaunchEventHandler;
import com.pem.logic.bean.provider.predicate.PredicateProvider;
import com.pem.logic.common.ServiceConstants;
import com.pem.model.predicate.bean.BeanPredicateObject;
import com.pem.model.predicate.common.PredicateObject;
import com.pem.persistence.api.manager.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@RegisterLaunchEventHandler(facade = ServiceConstants.DATABASE_SYNCHRONIZER_FACADE_NAME)
public class PredicateDatabaseSynchronizer implements LaunchEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredicateDatabaseSynchronizer.class);

    private PredicateProvider predicateProvider;
    private PersistenceManager persistenceManager;

    @Override
    public void handle() {
        Set<BeanObject> beanObjects = new HashSet<>(predicateProvider.getAllPredicateBeanObjects());

        persistenceManager.getAllByType(BeanPredicateObject.class)
                .doOnNext(beanPredicateObject -> LOGGER.debug("Try to handle Predicate {}.", beanPredicateObject))
                .subscribe(predicate -> {
                    if (!beanObjects.contains(predicate.getBean())) {
                        updateStatus(predicate, false);
                        return;
                    }

                    updateStatus(predicate, true);
                    beanObjects.remove(predicate.getBean());
                });

        beanObjects.forEach(beanObject -> createPredicate(beanObject));
    }

    private void updateStatus(PredicateObject predicateObject, boolean active) {
        LOGGER.debug("Update Status for predicateObject: {}.", predicateObject);
        if (active == predicateObject.isActive()) {
            return;
        }
        predicateObject.setActive(active);
        persistenceManager.update(predicateObject)
                .doOnError(Exception.class, exception -> {
                    throw new RuntimeException(exception);
                }).subscribe();
    }

    private void createPredicate(BeanObject beanObject) {
        LOGGER.debug("Create new predicateObject for {}.", beanObject);
        BeanPredicateObject predicateObject = new BeanPredicateObject();
        predicateObject.setActive(true);
        predicateObject.setName(beanObject.getName());
        predicateObject.setBean(beanObject);

        persistenceManager.create(predicateObject)
                .doOnError(Exception.class, exception -> {
                    throw new RuntimeException(exception);
                }).subscribe();
    }

    public void setPredicateProvider(PredicateProvider predicateProvider) {
        this.predicateProvider = predicateProvider;
    }

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }
}
