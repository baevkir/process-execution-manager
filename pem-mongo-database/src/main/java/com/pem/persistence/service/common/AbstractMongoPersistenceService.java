package com.pem.persistence.service.common;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.pem.persistence.common.CommonMongoRepository;
import com.pem.persistence.model.common.IdentifiableEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.List;

public abstract class AbstractMongoPersistenceService<E extends IdentifiableEntity, R extends CommonMongoRepository<E>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMongoPersistenceService.class);
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

    protected <O extends E> List<O> getAllByType(final Class<O> targetClass) {
        Assert.notNull(targetClass,"Class is NULL, can`t find Entities." );

        String className = targetClass.getCanonicalName();
        List<E> operationEntities = getRepository().findByImplementation(className);

        return FluentIterable.from(operationEntities).transform(new Function<E, O>() {
            @Override
            public O apply(E input) {
                Assert.isInstanceOf(targetClass, input);
                return targetClass.cast(input);
            }
        }).toList();
    }
    protected void delete(BigInteger id){
        Assert.notNull(id, "Id is empty, can`t delete Entity.");
        getRepository().delete(id);
    }
}
