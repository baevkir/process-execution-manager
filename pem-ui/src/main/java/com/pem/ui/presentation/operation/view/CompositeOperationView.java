package com.pem.ui.presentation.operation.view;

import com.google.common.eventbus.EventBus;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.ui.presentation.common.view.BeanFormView;
import com.pem.ui.presentation.common.view.OperationView;
import com.pem.ui.presentation.operation.event.ShowOperationsListEvent;
import com.pem.ui.presentation.operation.list.OperationsLoader;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@BeanFormView(SyncCompositeOperationDTO.class)
@OperationView("Consistent composite operation")
public class CompositeOperationView extends AbstractOperationView<SyncCompositeOperationDTO> implements OperationsLoader {

    @Autowired
    private EventBus eventBus;

    private TwinColSelect operationsSelect = new TwinColSelect("Operations");


    @Override
    public void bind(SyncCompositeOperationDTO bean) {
        super.bind(bean);
        eventBus.post(new ShowOperationsListEvent(this));
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

    @Override
    public void load(List<OperationDTO> operations) {
        operationsSelect.setContainerDataSource(new BeanItemContainer<>(OperationDTO.class, operations));
        operationsSelect.setItemCaptionPropertyId("name");
        getBinder().bind(operationsSelect, "operations");

        operationsSelect.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                setModified(true);
            }
        });
    }
}
