package com.pem.ui.configuration;

import com.google.common.eventbus.EventBus;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableVaadin
public class PemUIConfiguration {

    @Bean
    @UIScope
    public EventBus eventBus() {
        EventBus eventBus = new EventBus();
        return eventBus;
    }
}
