package com.pem.ui.presentation.operation.view.composite;

import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.ui.presentation.common.view.provider.BeanFormView;
import com.pem.ui.presentation.operation.view.BaseOperationView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@BeanFormView(SyncCompositeOperationDTO.class)
@com.pem.ui.presentation.common.view.provider.OperationView("Consistent composite operation")
public class CompositeOperationView extends BaseOperationView<SyncCompositeOperationDTO> {

    @Autowired
    private CompositeOperationPresenter presenter;

    private TwinColSelect operationsSelect = new TwinColSelect("Operations");

    @Override
    protected CompositeOperationPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void initFormElements() {
        super.initFormElements();
        operationsSelect.setRequired(true);
        operationsSelect.setLeftColumnCaption("Available");
        operationsSelect.setRightColumnCaption("Selected");
    }

    @Override
    protected Layout createFormComponent() {
        initFormElements();
        HorizontalLayout mainLayout = new HorizontalLayout();

        FormLayout leftFormLayout = new FormLayout();
        leftFormLayout.addComponent(getNameField());

        leftFormLayout.addComponent(getDescriptionField());
        leftFormLayout.addComponent(getCreatedWhenField());
        leftFormLayout.addComponent(getModifyWhenField());

        mainLayout.addComponent(leftFormLayout);
        mainLayout.addComponent(new VerticalSplitPanel());

        FormLayout rightFormLayout = new FormLayout();
        mainLayout.addComponent(rightFormLayout);
        rightFormLayout.addComponent(operationsSelect);
        operationsSelect.setMultiSelect(true);

        return mainLayout;
    }
}
