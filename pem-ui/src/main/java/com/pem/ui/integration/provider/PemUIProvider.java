package com.pem.ui.integration.provider;

import com.pem.core.common.applicationcontext.builder.ApplicationContextBuilder;
import com.pem.integration.provider.PemServiceProvider;
import com.pem.integration.provider.PemServiceProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.Map;

public class PemUIProvider extends AbstractSpringUIProvider implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(PemServiceProviderImpl.class);

    private ApplicationContext parentContext;
    private ApplicationContext applicationContext;
    private PemServiceProvider serviceProvider;
    private Map<String, String> parentBeans;


    @Override
    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @PostConstruct
    public void initUIProvider() {
        LOGGER.trace("Start to load PemUIProvider.");
        ApplicationContextBuilder contextBuilder = new ApplicationContextBuilder()
                .setParentContext(parentContext)
                .addXMLConfiguration("config/pem-vaadin-ui-config.xml");

        applicationContext = contextBuilder.build();

        detectUIs();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        parentContext = applicationContext;
    }


}
