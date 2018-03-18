package com.pem.ui.presentation.operation.popup.common.view;

import com.pem.model.common.BaseObject;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;

public interface PopupView<O extends BaseObject, E extends AbstractEditor> extends View {
    E getEditor();
    Binder<O> bind(O object);
    void redraw();
}
