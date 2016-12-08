package com.pem.logic.bean.synchronizer;

import com.pem.core.common.utils.ApplicationContextWrapper;
import com.pem.logic.bean.synchronizer.operation.BeanOperationDatabaseSynchronizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DatabaseSynchronizerFacade implements DatabaseSynchronizer, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanOperationDatabaseSynchronizer.class);
    private List<DatabaseSynchronizer> synchronizers = new ArrayList<>();
    private ApplicationContext applicationContext;

    @Override
    public void synchronize() {
        for (DatabaseSynchronizer synchronizer : synchronizers) {
            LOGGER.trace("Synchronize {}.", synchronizer.getClass());
            synchronizer.synchronize();
        }
    }

    @PostConstruct
    public void init() {
        fillDatabaseSynchronizers();
        synchronize();
    }

    private void fillDatabaseSynchronizers() {
        Map<String, DatabaseSynchronizer> synchronizerMap = applicationContext.getBeansOfType(DatabaseSynchronizer.class, false, true);
        ApplicationContextWrapper contextWrapper = new ApplicationContextWrapper(applicationContext);
        String currentFacadeName = contextWrapper.getBeanName(this);

        for (Map.Entry<String, DatabaseSynchronizer> synchronizer : synchronizerMap.entrySet()) {
            DatabaseSynchronizer value = synchronizer.getValue();
            Class<? extends DatabaseSynchronizer> clazz = (Class<? extends DatabaseSynchronizer>) AopProxyUtils.ultimateTargetClass(value);

            if (!clazz.isAnnotationPresent(RegisterDatabaseSynchronizer.class)) {
                continue;
            }
            RegisterDatabaseSynchronizer annotation = clazz.getAnnotation(RegisterDatabaseSynchronizer.class);
            List<String> factories = Arrays.asList(annotation.facades());
            if (factories.contains(currentFacadeName)) {
                addSynchronizer(value);
            }
        }
    }

    private void addSynchronizer(DatabaseSynchronizer synchronizer) {
        synchronizers.add(synchronizer);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
