package com.pem.ui.presentation.operation.view;

import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.view.BaseBeanForm;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

public abstract class BaseOperationView<O extends OperationDTO> extends BaseBeanForm<O> {
      @Override
    protected Layout createTopToolbar() {
        return new HorizontalLayout();
    }
}
