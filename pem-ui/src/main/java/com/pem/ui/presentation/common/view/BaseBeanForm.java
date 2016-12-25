package com.pem.ui.presentation.common.view;

import com.pem.model.common.BaseDTO;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.*;

public abstract class BaseBeanForm<B extends BaseDTO> extends AbstractBeanForm<B> {

    @PropertyId("name")
    private TextField nameField = new TextField();

    @PropertyId("description")
    private TextArea descriptionField = new TextArea();

    @PropertyId("createdWhen")
    private DateField createdWhenField = new DateField();

    @PropertyId("modifyWhen")
    private DateField modifyWhenField = new DateField();


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

    @Override
    protected void initFormElements() {
        super.initFormElements();
        nameField.setRequired(true);
        nameField.setCaption("Name");

        descriptionField.setNullRepresentation("");
        descriptionField.setCaption("Description");

        createdWhenField.setEnabled(false);
        createdWhenField.setCaption("Created");

        modifyWhenField.setEnabled(false);
        modifyWhenField.setCaption("Modify");
    }

}
