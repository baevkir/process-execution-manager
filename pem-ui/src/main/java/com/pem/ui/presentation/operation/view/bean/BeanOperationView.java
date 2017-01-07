package com.pem.ui.presentation.operation.view.bean;

import com.pem.model.operation.bean.BeanOperationDTO;
import com.pem.ui.presentation.common.view.provider.BeanFormView;
import com.pem.ui.presentation.operation.view.BaseOperationView;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;

@BeanFormView(BeanOperationDTO.class)
public class BeanOperationView extends BaseOperationView<BeanOperationDTO> {

    @Autowired
    private BeanOperationPresenter presenter;

    private TextField beanField = new TextField("Bean name");

    @Override
    protected BeanOperationPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void initFormElements() {
        setCaption("Bean operation");
        super.initFormElements();
        beanField.setReadOnly(true);
    }

    @Override
    protected Layout createFormComponent() {
        initFormElements();
        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(getNameField());

        formLayout.addComponent(beanField);

        formLayout.addComponent(getDescriptionField());
        formLayout.addComponent(getCreatedWhenField());
        formLayout.addComponent(getModifyWhenField());

        return formLayout;
    }
}
