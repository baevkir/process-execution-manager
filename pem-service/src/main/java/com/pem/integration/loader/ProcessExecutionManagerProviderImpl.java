package com.pem.integration.loader;

import com.pem.service.calculator.ConditionCalculatorService;
import com.pem.service.executor.OperationExecutor;
import com.pem.service.operation.OperationService;
import com.pem.service.process.ExecutionProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProcessExecutionManagerProviderImpl implements ProcessExecutionManagerProvider, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessExecutionManagerProviderImpl.class);

    private ApplicationContext parentContext;
    private ApplicationContext applicationContext;

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
    public ConditionCalculatorService getConditionCalculatorService() {
        return applicationContext.getBean(ConditionCalculatorService.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        parentContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        applicationContext = initContext();
    }

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    private ApplicationContext initContext(){
        LOGGER.trace("Start to load ProcessExecutionManagerContext.");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        applicationContext.setParent(parentContext);
        applicationContext.start();

        return applicationContext;
    }
}
