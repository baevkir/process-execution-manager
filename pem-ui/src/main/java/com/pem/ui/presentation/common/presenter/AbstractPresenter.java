package com.pem.ui.presentation.common.presenter;

import com.vaadin.navigator.View;

public abstract class AbstractPresenter<V extends View> implements Presenter<V> {

    private V view;

    @Override
    public void setView(V view) {
        this.view = view;
    }

    protected V getView() {
        return view;
    }
}
