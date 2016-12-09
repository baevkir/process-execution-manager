package com.pem.core.common.event;

import com.pem.core.common.utils.ApplicationContextWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LaunchEventBus implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(LaunchEventBus.class);
    private List<LaunchEventHandler> eventHandlers = new ArrayList<>();
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
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
        ApplicationContextWrapper contextWrapper = new ApplicationContextWrapper(applicationContext);
        String currentFacadeName = contextWrapper.getBeanName(this);

        for (Map.Entry<String, LaunchEventHandler> eventHandlerEntry : eventHandlerMap.entrySet()) {
            LaunchEventHandler value = eventHandlerEntry.getValue();
            Class<? extends LaunchEventHandler> clazz = (Class<? extends LaunchEventHandler>) AopProxyUtils.ultimateTargetClass(value);

            if (!clazz.isAnnotationPresent(RegisterLaunchEventHandler.class)) {
                continue;
            }
            RegisterLaunchEventHandler annotation = clazz.getAnnotation(RegisterLaunchEventHandler.class);
            String facade = annotation.facade();
            if (facade.equals(currentFacadeName)) {
                addEventHandler(value);
            }
        }
    }

    private void addEventHandler(LaunchEventHandler eventHandler) {
        eventHandlers.add(eventHandler);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
