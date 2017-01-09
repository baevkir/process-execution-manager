package com.pem.integration.provider;

import com.pem.core.common.applicationcontext.builder.ApplicationContextBuilder;
import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.service.calculator.CalculatorService;
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
    private static final String CALCULATOR_PERSISTENCE_SERVICE_BEAN = "calculatorPersistenceService";
    private static final String OPERATION_PERSISTENCE_SERVICE_BEAN = "operationPersistenceService";
    private static final String EXECUTION_RECORD_PERSISTENCE_SERVICE_BEAN = "executionRecordPersistenceService";
    private static final String PROCESS_PERSISTENCE_SERVICE_BEAN = "processPersistenceService";

    private ApplicationContext parentContext;
    private ApplicationContext applicationContext;
    private String applicationName;
    private PemPersistenceServiceProvider persistenceServiceProvider;
    private Map<String, String> parentBeans;

    @Override
    public CalculatorService getCalculatorService() {
        return applicationContext.getBean(CalculatorService.class);
    }

    @Override
    public ServiceEventBus getServiceEventBus() {
        return applicationContext.getBean(ServiceEventBus.class);
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
        ApplicationContextBuilder contextBuilder = new ApplicationContextBuilder()
                .setContextId(applicationName)
                .setParentContext(parentContext)
                .addXMLConfiguration("pemApplicationContext.xml")
                .addParentBeans(parentBeans)
                .addSingletonBean(PERSISTENCE_SERVICE_PROVIDER_BEAN, persistenceServiceProvider)
                .addSingletonBean(CALCULATOR_PERSISTENCE_SERVICE_BEAN, persistenceServiceProvider.getCalculatorPersistenceService())
                .addSingletonBean(OPERATION_PERSISTENCE_SERVICE_BEAN, persistenceServiceProvider.getOperationPersistenceService())
                .addSingletonBean(EXECUTION_RECORD_PERSISTENCE_SERVICE_BEAN, persistenceServiceProvider.getExecutionRecordPersistenceService())
                .addSingletonBean(PROCESS_PERSISTENCE_SERVICE_BEAN, persistenceServiceProvider.getProcessPersistenceService());

        applicationContext = contextBuilder.build();
    }

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        applicationName = name;
    }
}
