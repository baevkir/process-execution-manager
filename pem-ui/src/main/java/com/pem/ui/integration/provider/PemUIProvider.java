package com.pem.ui.integration.provider;

import com.pem.core.common.applicationcontext.builder.ApplicationContextBuilder;
import com.pem.integration.provider.PemServiceProvider;
import com.pem.integration.provider.PemServiceProviderImpl;
import com.vaadin.data.util.converter.ConverterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Map;

public class PemUIProvider extends AbstractSpringUIProvider implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(PemServiceProviderImpl.class);
    private static final String SERVICE_PROVIDER_BEAN = "serviceProvider";
    private static final String CALCULATOR_SERVICE_BEAN = "calculatorService";
    private static final String OPERATION_SERVICE_BEAN = "operationService";
    private static final String PROCESS_SERVICE_BEAN = "processService";

    private ApplicationContext parentContext;
    private ApplicationContext applicationContext;
    private PemServiceProvider serviceProvider;
    private Map<String, String> parentBeans;

    public void setServiceProvider(PemServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public void setParentBeans(Map<String, String> parentBeans) {
        this.parentBeans = parentBeans;
    }

    public ConverterFactory getDataConverterFactory(){
        return getApplicationContext().getBean(ConverterFactory.class);
    }

    @Override
    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @PostConstruct
    void initUIProvider() {
        Assert.notNull(serviceProvider, "PemServiceProvider undefined.");
        LOGGER.trace("Start to load PemUIProvider.");
        ApplicationContextBuilder contextBuilder = new ApplicationContextBuilder()
                .setParentContext(parentContext)
                .addParentBeans(parentBeans)
                .addXMLConfiguration("config/pem-vaadin-ui-config.xml")
                .addSingletonBean(SERVICE_PROVIDER_BEAN, serviceProvider)
                .addSingletonBean(CALCULATOR_SERVICE_BEAN, serviceProvider.getCalculatorService())
                .addSingletonBean(OPERATION_SERVICE_BEAN, serviceProvider.getOperationService())
                .addSingletonBean(PROCESS_SERVICE_BEAN, serviceProvider.getExecutionProcessService());

        applicationContext = contextBuilder.build();

        detectUIs();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        parentContext = applicationContext;
    }


}
