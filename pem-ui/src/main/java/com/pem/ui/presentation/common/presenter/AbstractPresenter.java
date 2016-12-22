package com.pem.ui.presentation.common.presenter;

import com.google.common.eventbus.EventBus;
import com.vaadin.navigator.View;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public abstract class AbstractPresenter<V extends View> {

    @Autowired
    private EventBus eventBus;

    private V view;

    public void bind(V view) {
        this.view = view;
    }

    protected V getView() {
        return view;
    }

    @PostConstruct
    public void init() {
        eventBus.register(this);
    }

    @PreDestroy
    public void destroy() {
        eventBus.unregister(this);
    }
}
