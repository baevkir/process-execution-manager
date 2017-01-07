package com.pem.ui.presentation.common.presenter;

import com.vaadin.navigator.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public abstract class BasePresenter<V extends View> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasePresenter.class);

    private V view;

    public final void bind(V view) {
        Assert.notNull(view, "Can't bind NULL view.");
        LOGGER.debug("Bind view {} to presenter {}.", view.getClass(), getClass());
        this.view = view;
        initViewHandlers();
    }

    protected abstract void initViewHandlers();

    protected V getView() {
        return view;
    }
}
