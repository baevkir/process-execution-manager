package com.pem.persistence.api.manager.impl;

import com.pem.model.common.BaseObject;
import com.pem.persistence.api.manager.PersistenceManager;
import com.pem.persistence.api.service.common.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.GenericTypeResolver;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class PersistenceManagerImpl implements PersistenceManager, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceManagerImpl.class);
    private ApplicationContext applicationContext;
    private Map<Class, PersistenceService> serviceMap = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <O extends BaseObject> Mono<O> create(O object) {
        return getServiceForType(object.getClass(), CreateService.class)
                .map(createService -> (CreateService<O>) createService)
                .flatMap(createService -> createService.create(object))
                .doOnNext(createdObject -> LOGGER.debug("Create object: {}.", object))
                .single();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <O extends BaseObject> Mono<Void> update(O object) {
        return getServiceForType(object.getClass(), UpdateService.class)
                .map(updateService -> (UpdateService<O>) updateService)
                .flatMap(updateService -> updateService.update(object)).single();
    }

    @Override
    public <O extends BaseObject> Mono<Void> delete(BigInteger id, Class<O> type) {
        return getServiceForType(type, DeleteService.class)
                .flatMap(deleteService -> deleteService.delete(id)).then();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <O extends BaseObject> Mono<O> getById(BigInteger id, Class<O> type) {
        return getServiceForType(type, GetByIdService.class)
                .map(getByIdService -> (GetByIdService<O>) getByIdService)
                .flatMap(getByIdService -> getByIdService.getById(id)).single();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <O extends BaseObject> Flux<O> getAll(Class<O> type) {
        return getServiceForType(type, GetAllService.class)
                .map(getAllService -> (GetAllService<O>) getAllService)
                .flatMap(getAllService -> getAllService.getAll());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <O extends BaseObject> Flux<O> getAllByType(Class<O> targetClass) {
        return getServiceForType(targetClass, GetAllByTypeService.class)
                .map(getAllByTypeService -> (GetAllByTypeService<O>) getAllByTypeService)
                .flatMap(getAllByTypeService -> getAllByTypeService.getAllByType(targetClass));
    }

    @Override
    public <S extends PersistenceService> S getService(Class<S> serviceClass) {
        return Flux.fromIterable(serviceMap.values())
                .ofType(serviceClass)
                .single()
                .doOnError(Exception.class, throwable -> {throw new RuntimeException("Can`t find service for class " + serviceClass.getCanonicalName(), throwable);})
                .block();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    void init() {
        Map<String, PersistenceService> services = applicationContext.getBeansOfType(PersistenceService.class);
        services.values().forEach(service ->{
            Class serviceClass = service.getClass();
            Class[] generics = GenericTypeResolver.resolveTypeArguments(serviceClass, PersistenceService.class);
            Class sClass = generics[0];
            LOGGER.trace("Add service {} for source {}.", serviceClass.getCanonicalName(), sClass.getCanonicalName());
            serviceMap.put(sClass, service);
        });
    }

    private <S, O> Mono<S> getServiceForType(Class<O> objectType, Class<S> serviceType) {
        return getServiceByObjectType(objectType)
                .cast(serviceType)
                .doOnError(Exception.class, exception -> {throw new RuntimeException(String.format("Method %s is not implemented for %s.", serviceType.getName(), objectType.getName()), exception);});
    }

    private Mono<PersistenceService> getServiceByObjectType(Class type) {
        Assert.notNull(type, "Can`t find Service for type");
        Assert.isTrue(!Object.class.equals(type), "Can`t find Service for type");

        return Mono.create(emitter -> {
            Class currentType = type;
            while (currentType != null && !Object.class.equals(currentType)) {
                PersistenceService persistenceService = serviceMap.get(currentType);
                if (persistenceService != null) {
                    emitter.success(persistenceService);
                    return;
                }
                currentType = currentType.getSuperclass();
            }
            emitter.error(new RuntimeException("Can`t find service for type " + type));
        });

    }
}
