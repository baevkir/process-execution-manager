package com.pem.persistence.mongo.service.trigger;

import com.pem.model.trigger.common.TriggerObject;
import com.pem.persistence.api.service.trigger.TriggerPersistenceService;
import com.pem.persistence.mongo.model.calculator.common.TriggerEntity;
import com.pem.persistence.mongo.repository.trigger.TriggerRepository;
import com.pem.persistence.mongo.service.common.AbstractMongoPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class MongoTriggerPersistenceService extends AbstractMongoPersistenceService<TriggerObject, TriggerEntity> implements TriggerPersistenceService {

    private TriggerRepository repository;

    @Override
    protected TriggerRepository getRepository() {
        return repository;
    }

    @Override
    public Mono<TriggerObject> create(TriggerObject object) {
        return internalCreate(object);
    }

    @Override
    public Mono<Void> update(TriggerObject object) {
        return internalUpdate(object);
    }

    @Override
    public Mono<Void> delete(BigInteger id) {
        return internalDelete(id);
    }

    @Override
    public Mono<TriggerObject> getById(BigInteger id) {
        return internalGetById(id);
    }

    @Override
    public <T extends TriggerObject> Flux<T> getAllByType(Class<T> type) {
        return internalGetAllByType(type);
    }

    @Override
    public Flux<TriggerObject> getAll() {
        return internalGetAll();
    }

    @Autowired
    public void setRepository(TriggerRepository repository) {
        this.repository = repository;
    }
}
