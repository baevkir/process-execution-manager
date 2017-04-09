package com.pem.ui.presentation.common.view.provider;

import com.pem.model.common.BaseObject;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;

import java.util.Set;

public interface PemViewProvider extends ViewProvider {
    View getView(Class<? extends BaseObject> beanDTOType);
    Set<OperationViewObject> getOperationViews();
}
