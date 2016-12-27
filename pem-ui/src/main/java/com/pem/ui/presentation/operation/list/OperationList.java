package com.pem.ui.presentation.operation.list;

import com.google.common.eventbus.EventBus;
import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.operation.event.ChooseNewOperationTypeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@UIScope
@SpringComponent
public class OperationList extends HorizontalLayout {

    @Autowired
    private EventBus eventBus;

    private boolean dataLoaded;
    private final Table operationTable = new Table();
    private final BeanItemContainer<OperationDTO> operationContainer = new BeanItemContainer<>(OperationDTO.class);

    public void load(List<OperationDTO> operations) {
        operationContainer.removeAllItems();
        operationContainer.addAll(operations);
        dataLoaded = true;
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    @PostConstruct
    void init() {
        dataLoaded = false;
        VerticalLayout mainLayout = new VerticalLayout();
        addComponent(mainLayout);

        mainLayout.addComponent(createTopToolbar());
        mainLayout.addComponent(operationTable);
        mainLayout.setExpandRatio(operationTable, 1.0f);

        operationTable.setSelectable(true);
        operationTable.setHeight("100%");

        operationTable.setContainerDataSource(operationContainer);
        operationTable.setVisibleColumns("name", "active");

        operationTable.setColumnHeader("name", "Name");
        operationTable.setColumnHeader("active", "Active");

        addSelectionListener();
    }

    private void addSelectionListener() {
        operationTable.addListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                OperationDTO operation = (OperationDTO) event.getItemId();
                UI.getCurrent().getNavigator().navigateTo(OperationListView.VIEW_NAME + "/" + operation.getId());
            }
        });
    }

    private Layout createTopToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addComponent(createNewOperationButton());

        return toolbar;
    }

    private Button createNewOperationButton() {
        Button newOperation = new Button("New operation");
        newOperation.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                eventBus.post(new ChooseNewOperationTypeEvent());
            }
        });

        return newOperation;
    }
}
