package com.pem.ui.presentation.operation.view;

import com.google.common.eventbus.EventBus;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.ui.presentation.common.view.BeanFormView;
import com.pem.ui.presentation.common.view.OperationView;
import com.pem.ui.presentation.operation.event.ShowOperationsListEvent;
import com.pem.ui.presentation.operation.list.OperationsLoader;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TwinColSelect;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@BeanFormView(SyncCompositeOperationDTO.class)
@OperationView("Consistent composite operation")
public class CompositeOperationView extends AbstractOperationView<SyncCompositeOperationDTO> implements OperationsLoader {

    @Autowired
    private EventBus eventBus;

    private TwinColSelect operationsSelect = new TwinColSelect("Operations");
    private final BeanItemContainer<OperationDTO> operationContainer = new BeanItemContainer<>(OperationDTO.class);

    @Override
    public void bind(SyncCompositeOperationDTO bean) {
        eventBus.post(new ShowOperationsListEvent(this));
        super.bind(bean);
    }

    @Override
    protected Layout createFormComponent() {
        HorizontalLayout mainLayout = new HorizontalLayout();
        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(getNameField());

        formLayout.addComponent(getDescriptionField());
        formLayout.addComponent(getCreatedWhenField());
        formLayout.addComponent(getModifyWhenField());

        mainLayout.addComponent(formLayout);

        operationsSelect.setNullSelectionAllowed(false);
        operationsSelect.setContainerDataSource(operationContainer);
        operationsSelect.setItemCaptionPropertyId("Name");
        operationsSelect.setMultiSelect(true);

        mainLayout.addComponent(operationsSelect);
        return mainLayout;
    }

    @Override
    public void load(List<OperationDTO> operations) {
        operationContainer.removeAllItems();
        operationContainer.addAll(operations);
    }
}
