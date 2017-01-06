package com.pem.ui.presentation.common.presenter;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.navigator.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseBeanPresenter<B, V extends View> extends BasePresenter<V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasePresenter.class);
    private BeanFieldGroup<B> binder;

    @Override
    public void bind(V view) {
        super.bind(view);

    }
}
