package com.pem.logic.service.predicate.impl;

import com.pem.logic.service.predicate.PredicateService;
import com.pem.model.predicate.common.PredicateObject;
import com.pem.persistence.api.manager.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class PredicateServiceImpl implements PredicateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredicateServiceImpl.class);

    private PersistenceManager persistenceManager;

    @Override
    public Mono<PredicateObject> createPredicate(PredicateObject predicateObject) {
        LOGGER.debug("Create new Condition: {}.", predicateObject);
        return persistenceManager.create(predicateObject);
    }

    @Override
    public Mono<Void> updatePredicate(PredicateObject predicateObject) {
        LOGGER.debug("Update Condition: {}.", predicateObject);
        return persistenceManager.update(predicateObject);
    }

    @Override
    public  Mono<Void> deletePredicate(BigInteger id) {
        LOGGER.debug("Delete Condition by id: {}.", id);
        return persistenceManager.delete(id, PredicateObject.class);
    }

    @Override
    public Mono<PredicateObject> getPredicate(BigInteger id) {
        LOGGER.debug("Get Condition by id: {}.", id);
        return persistenceManager.getById(id, PredicateObject.class);
    }

    @Override
    public Flux<PredicateObject> getAllPredicates() {
        LOGGER.debug("Get All ConditionCalculators.");
        return persistenceManager.getAll(PredicateObject.class);
    }

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }
}
