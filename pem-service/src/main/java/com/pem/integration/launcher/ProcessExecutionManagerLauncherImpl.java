package com.pem.integration.launcher;

import com.pem.logic.common.applicationcontext.ChildApplicationContextBuilder;
import com.pem.persistence.api.provider.PersistenceServiceProvider;
import com.pem.logic.service.calculator.CalculatorService;
import com.pem.logic.service.executor.OperationExecutor;
import com.pem.logic.service.operation.OperationService;
import com.pem.logic.service.process.ExecutionProcessService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.Map;

public class ProcessExecutionManagerLauncherImpl implements ProcessExecutionManagerLauncher, ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessExecutionManagerLauncherImpl.class);
    private static final String PERSISTENCE_SERVICE_PROVIDER_BEAN = "persistenceServiceProvider";
    private static final String CALCULATOR_PERSISTENCE_SERVICE_BEAN = "calculatorPersistenceService";
    private static final String OPERATION_PERSISTENCE_SERVICE_BEAN = "operationPersistenceService";
    private static final String EXECUTION_RECORD_PERSISTENCE_SERVICE_BEAN = "executionRecordPersistenceService";
    private static final String PROCESS_PERSISTENCE_SERVICE_BEAN = "processPersistenceService";

    private ApplicationContext parentContext;
    private ApplicationContext applicationContext;

    private PersistenceServiceProvider persistenceServiceProvider;
    private Map<String, String> parentBeans;

    @Override
    public ExecutionProcessService getExecutionProcessService() {
        return applicationContext.getBean(ExecutionProcessService.class);
    }

    @Override
    public OperationService getOperationService() {
        return applicationContext.getBean(OperationService.class);
    }

    @Override
    public OperationExecutor getOperationExecutor() {
        return applicationContext.getBean(OperationExecutor.class);
    }

    @Override
    public CalculatorService getConditionCalculatorService() {
        return applicationContext.getBean(CalculatorService.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        parentContext = applicationContext;
    }

    public void setParentBeans(Map<String, String> beans) {
        this.parentBeans = beans;
    }

    public void setPersistenceServiceProvider(PersistenceServiceProvider persistenceServiceProvider) {
        this.persistenceServiceProvider = persistenceServiceProvider;
    }

    @PostConstruct
    public void initApplicationContext() {
        LOGGER.trace("Start to load ProcessExecutionManagerContext.");
        ChildApplicationContextBuilder contextBuilder = new  ChildApplicationContextBuilder()
                .setParentContext(parentContext)
                .addXMLConfiguration("classpath:pemApplicationContext.xml")
                .addSingletonBean(PERSISTENCE_SERVICE_PROVIDER_BEAN, persistenceServiceProvider)
                .addSingletonBean(CALCULATOR_PERSISTENCE_SERVICE_BEAN, persistenceServiceProvider.getCalculatorPersistenceService())
                .addSingletonBean(OPERATION_PERSISTENCE_SERVICE_BEAN, persistenceServiceProvider.getOperationPersistenceService())
                .addSingletonBean(EXECUTION_RECORD_PERSISTENCE_SERVICE_BEAN, persistenceServiceProvider.getExecutionRecordPersistenceService())
                .addSingletonBean(PROCESS_PERSISTENCE_SERVICE_BEAN, persistenceServiceProvider.getProcessPersistenceService());

        if (MapUtils.isNotEmpty(parentBeans)) {
            contextBuilder.addParentBeans(parentBeans);
        }

        applicationContext = contextBuilder.build();
    }

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
