package com.pem.ui.presentation.common.view;

import com.pem.model.common.BaseDTO;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

public abstract class BeanFormPanel<B extends BaseDTO> extends FormLayout {

    private B beanDTO;

    private FieldGroup binder;

    @PropertyId("name")
    private TextField nameField = new TextField();

    @PropertyId("description")
    private TextField descriptionField = new TextField();

    private DateField createdWhenField = new DateField();

    private DateField modifyWhenField = new DateField();

    public void bind(B baseObject) {
        this.beanDTO = baseObject;
        BeanItem<B>beanItem = new BeanItem<>(baseObject);
        binder = new FieldGroup(beanItem);
        binder.setBuffered(true);
        binder.bindMemberFields(this);
    }

    protected FieldGroup getBinder() {
        return binder;
    }

    protected B getBeanDTO() {
        return beanDTO;
    }

    protected TextField getNameField() {
        return nameField;
    }

    protected TextField getDescriptionField() {
        return descriptionField;
    }

    protected DateField getCreatedWhenField() {
        return createdWhenField;
    }

    protected DateField getModifyWhenField() {
        return modifyWhenField;
    }
}
