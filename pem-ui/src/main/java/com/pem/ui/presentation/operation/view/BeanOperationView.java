package com.pem.ui.presentation.operation.view;

import com.pem.model.operation.bean.BeanOperationDTO;
import com.pem.ui.presentation.common.view.BeanFormPanel;
import com.pem.ui.presentation.common.view.BeanFormView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import javax.annotation.PostConstruct;

@BeanFormView(BeanOperationDTO.class)
public class BeanOperationView extends BeanFormPanel<BeanOperationDTO> implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    @PostConstruct
    void init() {
        addComponent(getNameField());
        addComponent(getDescriptionField());
        getDescriptionField().setNullRepresentation("");

        addComponent(getCreatedWhenField());
        getCreatedWhenField().setReadOnly(true);

        addComponent(getModifyWhenField());
        getModifyWhenField().setReadOnly(true);
    }
}
