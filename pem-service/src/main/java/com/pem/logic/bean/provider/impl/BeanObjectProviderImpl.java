package com.pem.logic.bean.provider.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.bean.BeansStream;
import com.pem.core.common.utils.ApplicationContextWrapper;
import com.pem.logic.bean.provider.BeanProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanObjectProviderImpl implements BeanProvider, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanObjectProviderImpl.class);
    private ApplicationContextWrapper contextWrapper;
    private String applicationId;
    private LoadingCache<Class<?>, Set<BeanObject>> beanObjectsCache;
    private Set<Class<?>> preloadedClasses;

    @Override
    public <O> O createInstance(String beanName, Class<O> type) {
        return contextWrapper.getPrototypeBeanByType(beanName, type);
    }

    @Override
    public <O> O createInstance(BeanObject beanObject, Class<O> type) {
        Assert.notNull(beanObject, "Bean object is NULL.");
        return contextWrapper.getPrototypeBeanByType(beanObject.getBeanName(), type);
    }

    @Override
    public Set<BeanObject> getAllForType(Class type) {
        return beanObjectsCache.getUnchecked(type);
    }

    private boolean checkAnnotation(SpringBeanObject annotation) {
        if (annotation.all()) {
            return true;
        }
        List<String> executors = Arrays.asList(annotation.executors());
        return executors.contains(applicationId);
    }

    @PostConstruct
    void init() {
        beanObjectsCache = CacheBuilder.newBuilder()
                .build(new BeanObjectsLoader());
        if (preloadedClasses != null) {
            preloadedClasses.forEach(aClass -> beanObjectsCache.refresh(aClass));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationId = applicationContext.getId();
        this.contextWrapper = new ApplicationContextWrapper(applicationContext);
    }

    public void setPreloadedClasses(Set<Class<?>> preloadedClasses) {
        this.preloadedClasses = preloadedClasses;
    }

    class BeanObjectsLoader extends CacheLoader<Class<?>, Set<BeanObject>> {
        @Override
        public Set<BeanObject> load(Class<?> key) throws Exception {
            return BeansStream.fromBeans(contextWrapper.findBeansByAnnotation(SpringBeanObject.class, key))
                    .filterWithAnnotation(SpringBeanObject.class, annotation -> checkAnnotation(annotation))
                    .calculateName(entry -> entry.getBeanAnnotation(SpringBeanObject.class).get().value())
                    .transformToBeanObject()
                    .collect(Collectors.toSet());
        }
    }
}
