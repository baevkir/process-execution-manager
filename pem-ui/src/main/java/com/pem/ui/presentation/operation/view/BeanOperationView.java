package com.pem.ui.presentation.operation.view;

import com.pem.model.operation.bean.BeanOperationDTO;
import com.pem.ui.presentation.common.view.BeanFormView;
import com.vaadin.data.fieldgroup.Caption;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;

@BeanFormView(BeanOperationDTO.class)
public class BeanOperationView extends AbstractOperationView<BeanOperationDTO> {

    @PropertyId("bean")
    @Caption("Bean")
    private ComboBox beanField = new ComboBox();

    @Override
    protected Layout createFormComponent() {
        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(getNameField());

        beanField.setReadOnly(true);
        beanField.setRequired(true);
        formLayout.addComponent(beanField);

        formLayout.addComponent(getDescriptionField());
        formLayout.addComponent(getCreatedWhenField());
        formLayout.addComponent(getModifyWhenField());

        return formLayout;
    }
}
