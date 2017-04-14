package com.pem.ui.presentation.operation.view;

import com.pem.model.operation.common.OperationObject;
import com.pem.ui.presentation.common.view.BaseBeanForm;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

public abstract class BaseOperationView<O extends OperationObject> extends BaseBeanForm<O> {

    private Button createProcess = new Button("New Process");

    @Override
    protected Layout createTopToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addComponent(createProcess);
        return toolbar;
    }
}
