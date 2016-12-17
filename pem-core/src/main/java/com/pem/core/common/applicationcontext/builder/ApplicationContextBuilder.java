package com.pem.core.common.applicationcontext.builder;

import com.pem.core.common.applicationcontext.ParentContextFactoryBean;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContextBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextBuilder.class);
    private String contextId;
    private ApplicationContext parentContext;
    private List<String> xmlConfigurations = new ArrayList<>();
    private final Map<String, String> parentBeans = new HashMap<>();
    private final Map<String, Object> singletonBeans = new HashMap<>();

    public ApplicationContextBuilder setParentContext(ApplicationContext parentContext) {
        this.parentContext = parentContext;
        return this;
    }

    public ApplicationContextBuilder addParentBean(String name, String nameInParentContext) {
        parentBeans.put(name, nameInParentContext);
        return this;
    }

    public ApplicationContextBuilder addParentBeans(Map<String, String> beansToAdd) {
        if (MapUtils.isNotEmpty(beansToAdd)) {
            parentBeans.putAll(beansToAdd);
        }
        return this;
    }

    public ApplicationContextBuilder addSingletonBean(String name, Object bean) {
        singletonBeans.put(name, bean);
        return this;
    }

    public ApplicationContextBuilder addXMLConfiguration(String path) {
        xmlConfigurations.add(path);
        return this;
    }

    public ApplicationContextBuilder setContextId(String contextId) {
        this.contextId = contextId;
        return this;
    }

    public ApplicationContext build() {
        Assert.notNull(parentContext, "Can't create Child Application Context. Parent Context is NULL.");

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        registerSingletonBeans(beanFactory);
        registerParentContextBeans(beanFactory);

        GenericApplicationContext context = new AnnotationConfigApplicationContext(beanFactory);

        if (StringUtils.isNotEmpty(contextId)) {
            context.setId(contextId);
        }

        context.setParent(parentContext);

        registerXMLConfigurations(context);
        context.refresh();

        return context;
    }

    private void registerXMLConfigurations(GenericApplicationContext context) {
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(context);
        for (String xmlConfiguration : xmlConfigurations) {
            xmlReader.loadBeanDefinitions(new ClassPathResource(xmlConfiguration));
        }
    }

    private void registerSingletonBeans(ConfigurableListableBeanFactory beanFactory) {
        Assert.notNull(beanFactory);
        for (Map.Entry<String, Object> entry : singletonBeans.entrySet()) {
            String beanName = entry.getKey();
            Object beanObject = entry.getValue();

            Assert.hasText(beanName, "Can't register Singleton Bean for Empty Bean Name");
            Assert.notNull(beanObject, "Can't register Singleton Bean for Null Bean Object");

            LOGGER.debug("Register Singleton Bean: {}.", beanName);
            beanFactory.registerSingleton(beanName, beanObject);
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
            Object bean = listableBeanFactory.getBean(beanName);
            Assert.notNull(bean, String.format("Can't initialize bean %s from %s.", beanName, parentBeanName));
        }
    }
}
