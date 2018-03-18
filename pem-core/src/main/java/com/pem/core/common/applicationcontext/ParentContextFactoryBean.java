package com.pem.core.common.applicationcontext;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ApplicationContext;

/**
 * Utility FactoryBean class for bean propagation from Parent Spring Context to child
 * with preserving the properties of bean options from parent context.
 *
 * {@param ParentContextFactoryBean.parentContext - Spring Context to load bean}
 * {@param ParentContextFactoryBean.parentBeanName - bean name in the parent context}
 * */
public class ParentContextFactoryBean extends AbstractFactoryBean {

    private ApplicationContext parentContext;
    private String parentBeanName;

    public void setParentBeanName(String parentBeanName) {
        this.parentBeanName = parentBeanName;
    }

    public void setParentContext(ApplicationContext parentContext) {
        this.parentContext = parentContext;
    }

    @Override
    public Class<?> getObjectType() {
        if (parentContext == null) {
            return null;
        }
        return parentContext.getType(parentBeanName);
    }

    @Override
    protected Object createInstance() throws Exception {
        return parentContext.getBean(parentBeanName);
    }

    @Override
    public boolean isSingleton() {
        return parentContext.isSingleton(parentBeanName);
    }

}
