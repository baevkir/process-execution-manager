package com.pem.logic.bean.synchronizer;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.bean.HasBeanObject;
import com.pem.core.common.event.LaunchEventHandler;
import com.pem.model.common.BaseObjectWithStatus;
import com.pem.persistence.api.manager.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractBeanDatabaseSynchronizer<O extends HasBeanObject> implements LaunchEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBeanDatabaseSynchronizer.class);

    private PersistenceManager persistenceManager;

    protected BeanObjectMapper<O> getMapper(Flux<O> entities, Flux<BeanObject> beanObjects){
        return new BeanObjectMapperImpl<>(entities, beanObjects);
    }

    protected <S extends BaseObjectWithStatus> Mono<Void> updateStatus(S source, boolean active) {
        return Flux.just(source)
                .doOnNext(entity -> LOGGER.debug("Update Status for {}.", entity))
                .filter(entity -> active != entity.isActive())
                .doOnNext(entity -> entity.setActive(active))
                .flatMap(entity -> persistenceManager.update(entity))
                .then();
    }

    protected PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    interface BeanObjectMapper<O extends HasBeanObject> {
        Flux<O> getForActivate();
        Flux<O> getForDeactivate();
        Flux<BeanObject> getForCreate();
    }

    private class BeanObjectMapperImpl<O extends HasBeanObject> implements BeanObjectMapper<O> {
        Flux<Map.Entry<BeanObject, Collection<O>>> mapper;

        public BeanObjectMapperImpl(Flux<O> entities, Flux<BeanObject> beanObjects) {
            Assert.notNull(entities, "Entities is null");
            Assert.notNull(entities, "Bean objects is null");
            Flux<Tuple2<BeanObject, O>> beanObjectsTuple = beanObjects.map(beanObject -> Tuples.of(beanObject, null));
            this.mapper =  entities
                    .map(entity -> Tuples.of(entity.getBean(), entity))
                    .concatWith(beanObjectsTuple)
                    .collectMultimap(objects -> objects.getT1(), objects -> objects.getT2())
                    .flatMapIterable(beanObjectMap -> beanObjectMap.entrySet())
                    .doOnNext(entry -> Assert.notEmpty(entry.getValue(), "Bean entry is empty."))
                    .doOnNext(entry -> Assert.isTrue(entry.getValue().size() < 3, "Bean entry size ia greater then 2."));
        }

        @Override
        public Flux<O> getForActivate() {
            return mapper
                    .filter(entry -> entry.getValue().size() == 2)
                    .map(entry -> getFirstNotNull(entry))
                    .doOnNext(entity -> Assert.isTrue(entity.isPresent(), "Cannot find entity."))
                    .map(entity -> entity.get());
        }

        @Override
        public Flux<O> getForDeactivate() {
            return getOneValueEntities()
                    .map(entry -> getFirstNotNull(entry))
                    .filter(entity -> entity.isPresent())
                    .map(entity -> entity.get());
        }

        @Override
        public Flux<BeanObject> getForCreate() {
            return getOneValueEntities()
                    .filter(entry -> !getFirstNotNull(entry).isPresent())
                    .map(entry -> entry.getKey());
        }

        private Flux<Map.Entry<BeanObject, Collection<O>>> getOneValueEntities() {
            return mapper
                    .filter(entry -> entry.getValue().size() < 2);
        }
        private Optional<O> getFirstNotNull(Map.Entry<BeanObject, Collection<O>> entry) {
            return entry.getValue().stream()
                    .filter(entity -> entity != null)
                    .findFirst();
        }
    }
}
