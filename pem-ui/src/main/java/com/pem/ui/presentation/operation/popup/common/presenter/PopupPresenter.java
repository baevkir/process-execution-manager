package com.pem.ui.presentation.operation.popup.common.presenter;

import com.pem.model.common.BaseObject;
import com.pem.ui.presentation.common.presenter.Presenter;
import com.pem.ui.presentation.operation.popup.common.view.PopupView;
import com.vaadin.data.Binder;

public interface PopupPresenter<O extends BaseObject, V extends PopupView<O, ?>> extends Presenter<V>{
    Binder<O> bind(O object);
}
