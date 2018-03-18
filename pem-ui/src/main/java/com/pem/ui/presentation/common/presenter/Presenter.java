package com.pem.ui.presentation.common.presenter;

import com.vaadin.navigator.View;

public interface Presenter<V extends View> {
    void setView(V view);
}
