package com.pem.persistence.service.common;

import com.pem.persistence.model.common.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.List;

public abstract class AbstractPersistenceService<E extends BaseEntity, R extends MongoRepository<E, BigInteger>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPersistenceService.class);
    protected abstract R getRepository();

    protected E create(E entity){
        Assert.notNull(entity, "Entity equals NULL.");
        entity.setId(null);
        return getRepository().save(entity);
    }

    protected void update(E entity){
        Assert.notNull(entity, "Entity equals NULL.");
        Assert.notNull(entity.getId(), "Id is empty, can`t create Entity.");
        getRepository().save(entity);
    }

    protected List<E> saveList(List<E> entities){
        return getRepository().save(entities);
    }

    protected E getOne(BigInteger id){
        Assert.notNull(id, "Id is empty, can`t find Entity.");
        LOGGER.debug("Start to find entity for {}", id);
        return getRepository().findOne(id);
    }

    protected List<E> getAll(){
        return getRepository().findAll();
    }

    protected void delete(BigInteger id){
        Assert.notNull(id, "Id is empty, can`t delete Entity.");
        getRepository().delete(id);
    }
}
