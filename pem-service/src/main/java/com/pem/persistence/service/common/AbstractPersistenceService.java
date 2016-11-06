package com.pem.persistence.service.common;

import com.pem.persistence.model.common.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.List;

@Service
@Transactional
public abstract class AbstractPersistenceService<E extends BaseEntity, R extends MongoRepository<E, BigInteger>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPersistenceService.class);
    protected abstract R getRepository();

    public E create(E entity){
        Assert.notNull(entity, "Entity equals NULL.");
        entity.setId(null);
        return getRepository().save(entity);
    }

    public void update(E entity){
        Assert.notNull(entity, "Entity equals NULL.");
        Assert.notNull(entity.getId(), "Id is empty, can`t create Entity.");
        getRepository().save(entity);
    }

    public List<E> saveList(List<E> entities){
        return getRepository().save(entities);
    }

    public E getOne(BigInteger id){
        Assert.notNull(id, "Id is empty, can`t find Entity.");
        LOGGER.debug("Start to find entity for {}", id);
        return getRepository().findOne(id);
    }

    public List<E> getAll(){
        return getRepository().findAll();
    }
}
