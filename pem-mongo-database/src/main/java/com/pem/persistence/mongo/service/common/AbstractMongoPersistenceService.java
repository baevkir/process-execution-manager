package com.pem.persistence.mongo.service.common;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.model.common.IdentifiableDTO;
import com.pem.persistence.mongo.common.CommonMongoRepository;
import com.pem.persistence.mongo.model.common.IdentifiableEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.List;

public abstract class AbstractMongoPersistenceService<O extends IdentifiableDTO, E extends IdentifiableEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMongoPersistenceService.class);

    private ConverterFactory converterFactory;

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    protected abstract CommonMongoRepository<E> getRepository();

    protected O create(O object){
        Assert.notNull(object, "Entity equals NULL.");
        object.setId(null);
        E entity = convertToEntity(object);
        return convertToObject(getRepository().insert(entity));
    }

    protected void update(O object){
        Assert.notNull(object, "Entity equals NULL.");
        Assert.notNull(object.getId(), "Id is empty, can`t create Entity.");
        E entity = convertToEntity(object);
        E updated = getRepository().save(entity);
        LOGGER.trace("Updated {}.", updated);
    }

    protected List<O> saveList(List<O> objects){
        List<E> entities = getRepository().save(convertAllToEntities(objects));
        return convertAllToObjects(entities);
    }

    protected O getOne(BigInteger id){
        Assert.notNull(id, "Id is empty, can`t find Entity.");
        LOGGER.debug("Start to find object for {}", id);
        E entity =getRepository().findOne(id);
        if (entity == null) {
            LOGGER.warn("Can't find {} by ID {}.", getObjectClass().getName(), id);
            return null;
        }

        return convertToObject(entity);
    }

    protected List<O> getAll(){
        return convertAllToObjects(getRepository().findAll());
    }

    protected <T extends O> List<T> getAllByType(final Class<T> targetClass) {
        Assert.notNull(targetClass,"Class is NULL, can`t find Entities." );

        Converter<T, E> converter = converterFactory.getConverter(targetClass, getEntityClass());
        Class converterClass = converter.getClass();
        Class[] generics = GenericTypeResolver.resolveTypeArguments(converterClass, Converter.class);
        Class<E> targetEntityClass = generics[1];

        String className = targetEntityClass.getCanonicalName();
        List<E> operationEntities = getRepository().findByImplementation(className);

        return FluentIterable.from(operationEntities).transform(new Function<E, T>() {
            @Override
            public T apply(E input) {
                O object = convertToObject(input);

                Assert.isInstanceOf(targetClass, object);
                return targetClass.cast(object);
            }
        }).toList();
    }
    protected void delete(BigInteger id){
        Assert.notNull(id, "Id is empty, can`t delete Entity.");
        getRepository().delete(id);
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

    protected List<E> convertAllToEntities(List<O> objects) {
        final Class<E> entityClass = getEntityClass();
        return FluentIterable.from(objects).transform(new Function<O, E>() {
            @Override
            public E apply(O input) {
                return converterFactory.convert(input, entityClass);
            }
        }).toList();
    }

    protected List<O> convertAllToObjects(List<E> entities) {
        final Class<O> objectClass = getObjectClass();
        return FluentIterable.from(entities).transform(new Function<E, O>() {
            @Override
            public O apply(E input) {
                return converterFactory.convert(input, objectClass);
            }
        }).toList();
    }

    private Class<E> getEntityClass() {
        Class converterClass = this.getClass();
        Class[] generics = GenericTypeResolver.resolveTypeArguments(converterClass, AbstractMongoPersistenceService.class);
        Class tClass = generics[1];

        return tClass;
    }

    private Class<O> getObjectClass() {
        Class converterClass = this.getClass();
        Class[] generics = GenericTypeResolver.resolveTypeArguments(converterClass, AbstractMongoPersistenceService.class);
        Class tClass = generics[0];

        return tClass;
    }


}
