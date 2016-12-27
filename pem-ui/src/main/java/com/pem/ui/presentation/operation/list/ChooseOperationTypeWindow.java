package com.pem.ui.presentation.operation.list;

import com.google.common.eventbus.EventBus;
import com.pem.ui.presentation.common.view.provider.OperationViewObject;
import com.pem.ui.presentation.common.view.provider.PemViewProvider;
import com.pem.ui.presentation.operation.event.OpenOperationEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringComponent
public class ChooseOperationTypeWindow extends Window {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private PemViewProvider viewProvider;

    private ListSelect operationTypes = new ListSelect();

    @PostConstruct
    void init() {
        setCaption("Choose type of operation for create.");
        setWidth("350px");
        setHeight("450px");
        center();
        setResizable(false);
        FormLayout mainLayout = new FormLayout();
        setContent(mainLayout);

        BeanItemContainer<OperationViewObject> operationViewsContainer = new BeanItemContainer<>(viewProvider.getOperationViews());
        operationTypes.setItemCaptionPropertyId("name");
        operationTypes.setMultiSelect(false);
        operationTypes.setNullSelectionAllowed(false);
        operationTypes.setContainerDataSource(operationViewsContainer);
        operationTypes.setSizeFull();

        mainLayout.addComponent(operationTypes);

        mainLayout.addComponent(createOKButton());
    }

    private Button createOKButton() {
        Button okButton = new Button("OK");
        okButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                startCreateOperation();
                close();
            }
        });

        return okButton;
    }

    private void startCreateOperation() {
        if (operationTypes.getValue() == null) {
            return;
        }
        OperationViewObject operationViewObject = (OperationViewObject) operationTypes.getValue();
        eventBus.post(new OpenOperationEvent(operationViewObject.getOperationType()));
    }
}
