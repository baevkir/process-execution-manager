package com.pem.ui.presentation.operation.view;

import com.pem.model.operation.bean.BeanOperationDTO;
import com.pem.ui.presentation.common.view.BeanFormView;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;

@BeanFormView(BeanOperationDTO.class)
public class BeanOperationView extends AbstractOperationView<BeanOperationDTO> {

    private TextField beanField = new TextField("Bean name");

    @Override
    protected void initFormElements() {
        setCaption("Bean operation");
        super.initFormElements();

        getBinder().bind(beanField, "bean.name");
        beanField.setReadOnly(true);
    }

    @Override
    protected Layout createFormComponent() {
        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(getNameField());

        formLayout.addComponent(beanField);

        formLayout.addComponent(getDescriptionField());
        formLayout.addComponent(getCreatedWhenField());
        formLayout.addComponent(getModifyWhenField());

        return formLayout;
    }
}
