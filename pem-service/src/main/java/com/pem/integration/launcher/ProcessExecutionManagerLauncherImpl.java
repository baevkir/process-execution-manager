package com.pem.integration.launcher;

import com.mongodb.Mongo;
import com.pem.integration.launcher.bean.ParentContextFactoryBean;
import com.pem.service.calculator.ConditionCalculatorService;
import com.pem.service.executor.OperationExecutor;
import com.pem.service.operation.OperationService;
import com.pem.service.process.ExecutionProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class ProcessExecutionManagerLauncherImpl implements ProcessExecutionManagerLauncher, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessExecutionManagerLauncherImpl.class);

    private ApplicationContext parentContext;
    private ApplicationContext applicationContext;

    private Mongo mongoDBConnect;
    private Map<String, String> beans;

    public void setMongoDBConnect(Mongo mongoDBConnect) {
        this.mongoDBConnect = mongoDBConnect;
    }

    public void setBeans(Map<String, String> beans) {
        this.beans = beans;
    }

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
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        context.setParent(parentContext);
        registerParentBeans(context);
        context.start();

        return context;
    }

    private void registerParentBeans(ClassPathXmlApplicationContext context) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        for (Map.Entry<String,String> entry : beans.entrySet()) {
            String beanName = entry.getKey();
            String parentBeanName = entry.getValue();

            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ParentContextFactoryBean.class);
            builder.addPropertyValue("parentBeanName", parentBeanName);
            builder.addPropertyValue("parentContext", parentContext);

            beanFactory.registerBeanDefinition(beanName, builder.getBeanDefinition());
        }
    }
}
