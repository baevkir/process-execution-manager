package com.pem.persistence.mongo.service.predicate;

import com.pem.model.predicate.common.PredicateObject;
import com.pem.persistence.api.service.predicate.PredicatePersistenceService;
import com.pem.persistence.mongo.model.predicate.common.PredicateEntity;
import com.pem.persistence.mongo.repository.predicate.PredicateRepository;
import com.pem.persistence.mongo.service.common.AbstractMongoPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class MongoPredicatePersistenceService  extends AbstractMongoPersistenceService<PredicateObject, PredicateEntity> implements PredicatePersistenceService{

    private PredicateRepository repository;

    @Override
    protected PredicateRepository getRepository() {
        return repository;
    }

    @Override
    public Mono<PredicateObject> create(PredicateObject object) {
        return internalCreate(object);
    }

    @Override
    public Mono<Void> update(PredicateObject object) {
        return internalUpdate(object);
    }

    @Override
    public Mono<PredicateObject> getById(BigInteger id) {
        return internalGetById(id);
    }

    @Override
    public Flux<PredicateObject> getAll() {
        return internalGetAll();
    }

    @Override
    public <T extends PredicateObject> Flux<T> getAllByType(Class<T> type) {
        return internalGetAllByType(type);
    }

    @Override
    public Mono<Void> delete(BigInteger id) {
        return internalDelete(id);
    }

    @Autowired
    public void setRepository(PredicateRepository repository) {
        this.repository = repository;
    }
}
