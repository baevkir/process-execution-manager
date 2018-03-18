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

/**
 * Utility Builder class for comfortable realtime dynamic spring context creation
 */
public class ApplicationContextBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextBuilder.class);
    private String contextId;
    private ApplicationContext parentContext;
    private List<String> xmlConfigurations = new ArrayList<>();
    private final Map<String, String> overrideBeans = new HashMap<>();
    private final Map<String, Object> singletonBeans = new HashMap<>();

    /**
     * Set parent context for builded context
     * @param parentContext
     */
    public ApplicationContextBuilder setParentContext(ApplicationContext parentContext) {
        this.parentContext = parentContext;
        return this;
    }

    /**
     * Add bean that should be overridden from parent context
     * @param name - Bean name in builded context that should be overridden
     * @param nameInParentContext - Bean name from Parent Context that should override our bean
     */
     public ApplicationContextBuilder addOverrideBean(String name, String nameInParentContext) {
        overrideBeans.put(name, nameInParentContext);
        return this;
    }

    /**
     * Bulk method ta add beasn that should be overridden from parent context
     * @param beansToAdd - Map that describe bean to override configuration.
     *                   {@param Map.Entry.key}   - Bean name in builded context that should be overridden
     *                   {@param Map.Entry.value} - Bean name from Parent Context that should override our bean
     */
    public ApplicationContextBuilder addOverrideBeans(Map<String, String> beansToAdd) {
        if (MapUtils.isNotEmpty(beansToAdd)) {
            overrideBeans.putAll(beansToAdd);
        }
        return this;
    }

    /**
     * Add bean to register in builded context
     * @param name - Bean name in builded context
     * @param bean - Instance of bean to be registered in builded context
     */
    public ApplicationContextBuilder addSingletonBean(String name, Object bean) {
        singletonBeans.put(name, bean);
        return this;
    }

    /**
     * Method add XML configuration to Builded context
     * @param path - path to xml file with Spring configuration
     */
    public ApplicationContextBuilder addXMLConfiguration(String path) {
        xmlConfigurations.add(path);
        return this;
    }

    /**
     * Sets context id to identify context
     * @param contextId
     */
    public ApplicationContextBuilder setContextId(String contextId) {
        this.contextId = contextId;
        return this;
    }

    /**
     * Build method to create {@link ApplicationContext} with preconfigured parameters.
     * @return ApplicationContext
     */
    public ApplicationContext build() {
        Assert.notNull(parentContext, "Can't create Child Application Context. Parent Context is NULL.");

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        registerSingletonBeans(beanFactory);
        registerXMLConfigurations(beanFactory);
        registerParentContextBeans(beanFactory);

        GenericApplicationContext context = new AnnotationConfigApplicationContext(beanFactory);

        if (StringUtils.isNotEmpty(contextId)) {
            context.setId(contextId);
        }

        context.setParent(parentContext);
        context.refresh();

        return context;
    }

    private void registerXMLConfigurations(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(beanFactory);
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
            Assert.notNull(beanObject, String.format("Can't register Singleton Bean %s for Null Bean Object", beanName));

            LOGGER.debug("Register Singleton Bean: {}.", beanName);
            beanFactory.registerSingleton(beanName, beanObject);
        }
    }

    private void registerParentContextBeans(ConfigurableListableBeanFactory beanFactory) {
        Assert.isInstanceOf(DefaultListableBeanFactory.class, beanFactory);
        DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        for (Map.Entry<String, String> entry : overrideBeans.entrySet()) {
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
