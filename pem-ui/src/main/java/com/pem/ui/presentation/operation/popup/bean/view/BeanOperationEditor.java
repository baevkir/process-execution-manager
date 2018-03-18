package com.pem.ui.presentation.operation.popup.bean.view;

import com.pem.core.common.annotation.spring.PrototypeComponent;
import com.pem.ui.presentation.operation.popup.common.view.AbstractEditor;
import com.vaadin.ui.TextField;

@PrototypeComponent
public class BeanOperationEditor extends AbstractEditor {
    private TextField beanField = new TextField("Bean name");

    public TextField getBeanField() {
        return beanField;
    }
}
