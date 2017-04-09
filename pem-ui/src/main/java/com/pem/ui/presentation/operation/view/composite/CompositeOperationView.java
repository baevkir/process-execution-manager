package com.pem.ui.presentation.operation.view.composite;

import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.ui.presentation.common.view.provider.BeanFormView;
import com.pem.ui.presentation.common.view.provider.OperationView;
import com.pem.ui.presentation.operation.view.BaseOperationView;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@BeanFormView(SyncCompositeOperationDTO.class)
@OperationView("Consistent composite operation")
public class CompositeOperationView extends BaseOperationView<SyncCompositeOperationDTO> {

    @Autowired
    private CompositeOperationPresenter presenter;

    @PropertyId("operations")
    private TwinColSelect operationsSelect = new TwinColSelect("Operations");

    private BeanItemContainer<OperationObject> operationsContainer;

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
        operationsContainer = new BeanItemContainer<>(OperationObject.class);
        operationsSelect.setContainerDataSource(operationsContainer);
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

    public Mono<Void> load(Flux<OperationObject> operationsPublisher) {
        return operationsPublisher.doOnSubscribe(subscription -> operationsSelect.removeAllItems())
                .doOnSubscribe(subscription -> operationsSelect.setItemCaptionPropertyId("name"))
                .doOnNext(operation -> operationsContainer.addBean(operation))
                .then();
    }
}
