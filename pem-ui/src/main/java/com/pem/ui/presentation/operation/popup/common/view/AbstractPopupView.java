package com.pem.ui.presentation.operation.popup.common.view;

import com.pem.model.common.BaseObject;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.FormLayout;

public abstract class AbstractPopupView<O extends BaseObject, E extends AbstractEditor> extends FormLayout implements PopupView<O, E> {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
