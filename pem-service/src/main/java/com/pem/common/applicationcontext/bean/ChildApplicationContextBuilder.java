package com.pem.common.applicationcontext.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChildApplicationContextBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChildApplicationContextBuilder.class);
    private ApplicationContext parentContext;
    private List<String> xmlConfigurations = new ArrayList<>();
    private final Map<String, String> parentBeans = new HashMap<>();
    private final Map<String, Object> singletonBeans = new HashMap<>();

    public ChildApplicationContextBuilder setParentContext(ApplicationContext parentContext) {
        this.parentContext = parentContext;
        return this;
    }

    public ChildApplicationContextBuilder addParentBean(String name, String nameInParentContext) {
        parentBeans.put(name, nameInParentContext);
        return this;
    }

    public ChildApplicationContextBuilder addParentBeans(Map<String, String> beansToAdd) {
        parentBeans.putAll(beansToAdd);
        return this;
    }

    public ChildApplicationContextBuilder addSingletonBean(String name, Object bean) {
        singletonBeans.put(name, bean);
        return this;
    }

    public ChildApplicationContextBuilder addXMLConfiguration(String path) {
        xmlConfigurations.add(path);
        return this;
    }

    public ApplicationContext build() {
        Assert.notNull(parentContext, "Can't create Child Application Context. Parent Context is NULL.");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(parentContext);
        context.refresh();
        context.setConfigLocations(getConfigLocations());

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        registerSingletonBeans(beanFactory);
        registerParentContextBeans(beanFactory);
        context.refresh();
        return context;
    }

    private String[] getConfigLocations() {
        return xmlConfigurations.toArray(new String[xmlConfigurations.size()]);
    }

    private void registerSingletonBeans(ConfigurableListableBeanFactory beanFactory) {
        Assert.notNull(beanFactory);
        for (Map.Entry<String, Object> entry : singletonBeans.entrySet()) {
            String beanName = entry.getKey();
            Object beanObject = entry.getValue();

            Assert.hasText(beanName, "Can't register Singleton Bean for Empty Bean Name");
            Assert.notNull(beanObject, "Can't register Singleton Bean for Null Bean Object");

            LOGGER.debug("Register Singleton Bean: {}.", beanName);
            beanFactory.initializeBean(beanObject, beanName);
        }
    }

    private void registerParentContextBeans(ConfigurableListableBeanFactory beanFactory) {
        Assert.isInstanceOf(DefaultListableBeanFactory.class, beanFactory);
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        for (Map.Entry<String, String> entry : parentBeans.entrySet()) {
            String beanName = entry.getKey();
            String parentBeanName = entry.getValue();

            Assert.hasText(beanName, "Can't register Bean for Empty Bean Name");
            Assert.hasText(parentBeanName, "Can't register Bean for Empty Parent Bean Name");

            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ParentContextFactoryBean.class);
            builder.addPropertyValue("parentBeanName", parentBeanName);
            builder.addPropertyValue("parentContext", parentContext);

            listableBeanFactory.registerBeanDefinition(beanName, builder.getBeanDefinition());
        }
    }
}
