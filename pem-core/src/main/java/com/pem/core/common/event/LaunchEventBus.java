package com.pem.core.common.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Event buss collect all {@link LaunchEventHandler} that subscribe this EventBus each time when
 * it created in Spring context and runAll of them.
 *
 * To subscribe {@link LaunchEventHandler} to this eventBus you should create implement
 * it with annotation {@link RegisterLaunchEventHandler} and spring bean name of LaunchEventBus
 */
public class LaunchEventBus implements ApplicationContextAware, BeanNameAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(LaunchEventBus.class);
    private String eventBusName;
    private List<LaunchEventHandler> eventHandlers = new ArrayList<>();
    private ApplicationContext applicationContext;

    @PostConstruct
    void init() {
        fillEventHandlers();
        fireEvent();
    }

    private void fireEvent() {
        for (LaunchEventHandler eventHandler : eventHandlers) {
            LOGGER.trace("Event Handler: {}.", eventHandler.getClass());
            eventHandler.handle();
        }
    }

    private void fillEventHandlers() {
        Map<String, LaunchEventHandler> eventHandlerMap = applicationContext.getBeansOfType(LaunchEventHandler.class, false, true);

        for (Map.Entry<String, LaunchEventHandler> eventHandlerEntry : eventHandlerMap.entrySet()) {
            LaunchEventHandler value = eventHandlerEntry.getValue();
            Class<? extends LaunchEventHandler> clazz = (Class<? extends LaunchEventHandler>) AopProxyUtils.ultimateTargetClass(value);

            if (!clazz.isAnnotationPresent(RegisterLaunchEventHandler.class)) {
                continue;
            }
            RegisterLaunchEventHandler annotation = clazz.getAnnotation(RegisterLaunchEventHandler.class);
            String facade = annotation.facade();
            if (facade.equals(eventBusName)) {
                eventHandlers.add(value);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String beanName) {
        eventBusName = beanName;
    }
}
