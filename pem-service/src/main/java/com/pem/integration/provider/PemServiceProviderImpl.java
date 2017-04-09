package com.pem.integration.provider;

import com.pem.core.common.applicationcontext.builder.ApplicationContextBuilder;
import com.pem.logic.service.trigger.TriggerService;
import com.pem.logic.service.operation.OperationService;
import com.pem.logic.service.process.ExecutionProcessService;
import com.pem.persistence.api.provider.PemPersistenceServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.Map;

public class PemServiceProviderImpl implements PemServiceProvider, ApplicationContextAware, BeanNameAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(PemServiceProviderImpl.class);
    private static final String PERSISTENCE_SERVICE_PROVIDER_BEAN = "persistenceServiceProvider";
    private static final String PERSISTENCE_MANAGER = "pem.persistenceManager";

    private ApplicationContext parentContext;
    private ApplicationContext applicationContext;
    private String applicationName;
    private PemPersistenceServiceProvider persistenceServiceProvider;
    private Map<String, String> parentBeans;

    @Override
    public TriggerService getTriggerService() {
        return applicationContext.getBean(TriggerService.class);
    }

    @Override
    public OperationService getOperationService() {
        return applicationContext.getBean(OperationService.class);
    }

    @Override
    public ExecutionProcessService getExecutionProcessService() {
        return applicationContext.getBean(ExecutionProcessService.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        parentContext = applicationContext;
    }

    public void setParentBeans(Map<String, String> beans) {
        this.parentBeans = beans;
    }

    public void setPersistenceServiceProvider(PemPersistenceServiceProvider persistenceServiceProvider) {
        this.persistenceServiceProvider = persistenceServiceProvider;
    }

    @PostConstruct
    void initApplicationContext() {
        LOGGER.trace("Start to load ProcessExecutionManagerContext.");
        applicationContext = new ApplicationContextBuilder()
                .setContextId(applicationName)
                .setParentContext(parentContext)
                .addXMLConfiguration("pemApplicationContext.xml")
                .addParentBeans(parentBeans)
                .addSingletonBean(PERSISTENCE_SERVICE_PROVIDER_BEAN, persistenceServiceProvider)
                .addSingletonBean(PERSISTENCE_MANAGER, persistenceServiceProvider.getPersistenceManager())
                .build();
    }

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        applicationName = name;
    }
}
