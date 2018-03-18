package com.pem.ui.presentation.operation.popup.common.view;

import com.vaadin.annotations.PropertyId;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public abstract class AbstractEditor {
    @PropertyId("name")
    private TextField nameField = new TextField("Name");
    @PropertyId("description")
    private TextArea descriptionField = new TextArea("Description");
    @PropertyId("createdWhen")
    private DateField createdWhenField = new DateField("Created");
    @PropertyId("modifyWhen")
    private DateField modifyWhenField = new DateField("Modify");

    public TextField getNameField() {
        return nameField;
    }

    public TextArea getDescriptionField() {
        return descriptionField;
    }

    public DateField getCreatedWhenField() {
        return createdWhenField;
    }

    public DateField getModifyWhenField() {
        return modifyWhenField;
    }
}
