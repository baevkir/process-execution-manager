package com.pem.persistence.api.manager;

import com.pem.model.common.BaseObject;
import com.pem.persistence.api.service.common.PersistenceService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface PersistenceManager {
    <O extends BaseObject> Mono<O> create(O object);
    <O extends BaseObject> Mono<Void> update(O object);
    <O extends BaseObject> Mono<Void> delete(BigInteger id, Class<O> type);
    <O extends BaseObject> Mono<O> getById(BigInteger id, Class<O> type);
    <O extends BaseObject> Flux<O> getAll(Class<O> type);
    <O extends BaseObject> Flux<O> getAllByType(Class<O> targetClass);
    <S extends PersistenceService> S getService(Class<S> serviceClass);
}
