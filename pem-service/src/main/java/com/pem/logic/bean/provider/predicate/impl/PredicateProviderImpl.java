package com.pem.logic.bean.provider.predicate.impl;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.bean.BeanObjectBuilder;
import com.pem.core.common.bean.BeansStream;
import com.pem.core.common.utils.ApplicationContextWrapper;
import com.pem.core.predicate.Predicate;
import com.pem.logic.bean.provider.predicate.PredicateProvider;
import com.pem.logic.bean.provider.trigger.impl.TriggerProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class PredicateProviderImpl implements PredicateProvider, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerProviderImpl.class);

    private ApplicationContextWrapper contextWrapper;

    @Override
    public <P extends Predicate> P createPredicate(String beanName, Class<P> predicateClass) {
        return contextWrapper.getPrototypeBeanByType(beanName, predicateClass);
    }

    @Override
    public Set<BeanObject> getAllPredicateBeanObjects() {
        LOGGER.debug("Get All Predicates.");
        final String applicationId = contextWrapper.getApplicationContext().getId();
        Map<String, Predicate> beans = contextWrapper.findBeansByAnnotation(GlobalPredicate.class, Predicate.class);

        return BeansStream.fromBeans(beans)
                .filterWithAnnotation(GlobalPredicate.class, annotation -> checkAnnotation(annotation, applicationId))
                .transform(predicateEntry -> transformToBeanObject(predicateEntry))
                .collect(Collectors.toSet());
    }

    private boolean checkAnnotation(GlobalPredicate annotation, String applicationId) {
        if (annotation.all()) {
            return true;
        }
        List<String> executors = Arrays.asList(annotation.executors());
        return executors.contains(applicationId);
    }

    private BeanObject transformToBeanObject(BeansStream.Entry<Predicate> entry) {
        return BeanObjectBuilder.newInstance()
                .setBeanName(entry.getBeanName())
                .setName(entry.getBeanAnnotation(GlobalPredicate.class).get().value())
                .build();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        contextWrapper = new ApplicationContextWrapper(applicationContext);
    }
}
