package com.pem.persistence.mongo.service.common;

import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.model.common.IdentifiableDTO;
import com.pem.persistence.mongo.common.CommonMongoRepository;
import com.pem.persistence.mongo.model.common.IdentifiableEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public abstract class AbstractMongoPersistenceService<O extends IdentifiableDTO, E extends IdentifiableEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMongoPersistenceService.class);

    private ConverterFactory converterFactory;

    private Class<O> objectClass;

    private Class<E> entityClass;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    protected abstract CommonMongoRepository<E> getRepository();

    protected Mono<O> create(O monoObject) {
        Assert.notNull(monoObject, "Entity equals NULL.");
        return Mono.just(monoObject)
                .doOnNext(object -> object.setId(null))
                .map(object -> convertToEntity(object))
                .map(entity -> getRepository().insert(entity))
                .map(entity -> convertToObject(entity));
    }

    protected Mono<Void> update(O monoObject) {
        Assert.notNull(monoObject, "Entity equals NULL.");
        return Mono.just(monoObject)
                .doOnNext(object -> Assert.notNull(object.getId(), "Id is empty, can`t create Entity."))
                .map(object -> convertToEntity(object))
                .map(entity -> getRepository().save(entity))
                .doOnNext(entity -> LOGGER.trace("Updated {}.", entity))
                .then();
    }

    protected Mono<O> getOne(BigInteger id) {
        Assert.notNull(id, "Id is empty, can`t find Entity.");
        LOGGER.debug("Start to find object for {}", id);

        return Mono.just(id)
                .map(currentId -> Optional.ofNullable(getRepository().findOne(currentId)))
                .map(objectOptional -> {
                    if (!objectOptional.isPresent()) {
                        LOGGER.warn("Can't find {} by ID {}.", getEntityClass().getName(), id);
                    }

                    return objectOptional.map(entity -> convertToObject(entity)).orElse(null);
                });
    }

    protected Flux<O> getAll() {
        return Flux.fromIterable(getRepository().findAll())
                .map(entity -> convertToObject(entity));
    }

    protected <T extends O> Flux<T> getAllByType(final Class<T> targetClass) {
        Assert.notNull(targetClass, "Class is NULL, can`t find Entities.");

        Flux<E> flux = Flux.create(fluxSink -> {
            Converter<T, E> converter = converterFactory.getConverter(targetClass, getEntityClass());
            Class converterClass = converter.getClass();
            Class[] generics = GenericTypeResolver.resolveTypeArguments(converterClass, Converter.class);
            Class<E> targetEntityClass = generics[1];

            String className = targetEntityClass.getCanonicalName();
            List<E> operationEntities = getRepository().findByImplementation(className);
            operationEntities
                    .stream()
                    .forEach(entity -> fluxSink.next(entity));
            fluxSink.complete();
        });


        return flux.map(entity -> convertToObject(entity))
                .cast(targetClass);
    }

    protected Mono<Void> delete(BigInteger id) {
        Assert.notNull(id, "Id is empty, can`t delete Entity.");

        return Mono.just(id)
                .doOnNext(currentId -> getRepository().delete(currentId))
                .then();
    }

    protected ConverterFactory getConverterFactory() {
        return converterFactory;
    }

    protected E convertToEntity(O object) {
        return converterFactory.convert(object, getEntityClass());
    }

    protected O convertToObject(E entity) {
        return converterFactory.convert(entity, getObjectClass());
    }

    private Class<E> getEntityClass() {
        if (entityClass == null) {
            Class converterClass = this.getClass();
            Class[] generics = GenericTypeResolver.resolveTypeArguments(converterClass, AbstractMongoPersistenceService.class);
            entityClass = generics[1];
        }

        return entityClass;
    }

    private Class<O> getObjectClass() {
        if (objectClass == null) {
            Class converterClass = this.getClass();
            Class[] generics = GenericTypeResolver.resolveTypeArguments(converterClass, AbstractMongoPersistenceService.class);
            objectClass = generics[0];
        }

        return objectClass;
    }


}
