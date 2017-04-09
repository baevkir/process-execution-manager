package com.pem.logic.service.trigger.impl;

import com.pem.logic.service.trigger.TriggerService;
import com.pem.model.trigger.common.TriggerObject;
import com.pem.persistence.api.manager.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class TriggerServiceImpl implements TriggerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerServiceImpl.class);

    private PersistenceManager persistenceManager;

    @Override
    public Mono<TriggerObject> createTrigger(TriggerObject triggerObject) {
        LOGGER.debug("Create new Condition: {}.", triggerObject);
        return persistenceManager.create(triggerObject);
    }

    @Override
    public Mono<Void> updateTrigger(TriggerObject triggerObject) {
        LOGGER.debug("Update Condition: {}.", triggerObject);
        return persistenceManager.update(triggerObject);
    }

    @Override
    public  Mono<Void> deleteTrigger(BigInteger id) {
        LOGGER.debug("Delete Condition by id: {}.", id);
        return persistenceManager.delete(id, TriggerObject.class);
    }

    @Override
    public Mono<TriggerObject> getTrigger(BigInteger id) {
        LOGGER.debug("Get Condition by id: {}.", id);
        return persistenceManager.getById(id, TriggerObject.class);
    }

    @Override
    public Flux<TriggerObject> getAllTriggers() {
        LOGGER.debug("Get All ConditionCalculators.");
        return persistenceManager.getAll(TriggerObject.class);
    }

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }
}
