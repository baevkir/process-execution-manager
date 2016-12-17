package com.pem.core.common.applicationcontext.builder;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class ApplicationContextBuilder extends AbstractApplicationContextBuilder<GenericApplicationContext> {

    @Override
    protected GenericApplicationContext createInstance(DefaultListableBeanFactory beanFactory) {
        return new AnnotationConfigApplicationContext(beanFactory);
    }
}
