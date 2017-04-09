package com.pem.ui.presentation.common.view;

import com.pem.model.common.BaseObject;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.*;

public abstract class BaseBeanForm<B extends BaseObject> extends AbstractBeanForm<B> {

    @PropertyId("name")
    private TextField nameField = new TextField("Name");

    @PropertyId("description")
    private TextArea descriptionField = new TextArea("Description");

    @PropertyId("createdWhen")
    private DateField createdWhenField = new DateField("Created");

    @PropertyId("modifyWhen")
    private DateField modifyWhenField = new DateField("Modify");


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

    protected void initFormElements() {
        nameField.setRequired(true);
        nameField.setNullRepresentation("");
        descriptionField.setNullRepresentation("");
        createdWhenField.setReadOnly(true);
        modifyWhenField.setReadOnly(true);
    }
}
