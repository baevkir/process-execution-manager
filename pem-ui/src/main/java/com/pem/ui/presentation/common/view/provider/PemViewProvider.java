package com.pem.ui.presentation.common.view.provider;

import com.pem.model.common.BaseDTO;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;

public interface PemViewProvider extends ViewProvider {
    View getView(BaseDTO beanDTO);
}
