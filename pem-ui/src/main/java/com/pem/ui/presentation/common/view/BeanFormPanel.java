package com.pem.ui.presentation.common.view;

import com.pem.model.common.BaseDTO;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TextArea;

public abstract class BeanFormPanel<B extends BaseDTO> extends FormLayout {

    private B beanDTO;

    private FieldGroup binder;

    @PropertyId("name")
    private TextField nameField = new TextField();

    @PropertyId("description")
    private TextArea descriptionField = new TextArea();

    @PropertyId("createdWhen")
    private DateField createdWhenField = new DateField();

    @PropertyId("modifyWhen")
    private DateField modifyWhenField = new DateField();

    public void bind(B baseObject) {
        this.beanDTO = baseObject;
        BeanItem<B> beanItem = new BeanItem<>(baseObject);
        binder = new BeanFieldGroup<>(baseObject.getClass());
        binder.setItemDataSource(beanItem);
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

    protected TextArea getDescriptionField() {
        return descriptionField;
    }

    protected DateField getCreatedWhenField() {
        return createdWhenField;
    }

    protected DateField getModifyWhenField() {
        return modifyWhenField;
    }
}
