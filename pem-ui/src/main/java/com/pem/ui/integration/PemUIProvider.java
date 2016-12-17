package com.pem.ui.integration;

import com.pem.integration.launcher.ProcessExecutionManagerLauncherImpl;
import com.vaadin.spring.server.SpringUIProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

public class PemUIProvider extends SpringUIProvider implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessExecutionManagerLauncherImpl.class);

    private ApplicationContext parentContext;
    private WebApplicationContext applicationContext;

    public PemUIProvider() {
        super(null);
    }

    @Override
    protected WebApplicationContext getWebApplicationContext() {
        return applicationContext;
    }

    @PostConstruct
    public void initApplicationContext() {
        LOGGER.trace("Start to load ProcessExecutionManagerContext.");
//        ApplicationContextBuilder contextBuilder = new ApplicationContextBuilder()
//                .setParentContext(parentContext)
//                .addXMLConfiguration("pemApplicationContext.xml");
//
//        applicationContext = contextBuilder.build();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        parentContext = applicationContext;
    }
}
