package com.pem.ui.presentation.operation.list;

import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.reactor.VaadinReactor;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.math.BigInteger;

@UIScope
@SpringComponent
public class OperationList extends HorizontalLayout {

    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_STATUS = "active";

    private boolean dataLoaded;
    private Button newOperationButton;
    private Button refreshButton;

    private final Table operationTable = new Table();
    private final BeanItemContainer<OperationDTO> operationContainer = new BeanItemContainer<>(OperationDTO.class);

    public void load(Flux<OperationDTO> operationPublisher) {
        operationPublisher.doOnSubscribe(subscription -> operationContainer.removeAllItems())
                .doOnSubscribe(subscription -> dataLoaded = true)
                .subscribe(operation -> operationContainer.addBean(operation));
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    public Flux<Button.ClickEvent> getNewOperationPublisher() {
        return VaadinReactor.buttonClickPublisher(newOperationButton);
    }

    public Flux<Button.ClickEvent> getRefreshPublisher() {
        return VaadinReactor.buttonClickPublisher(refreshButton);
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
        operationTable.setHeight("250px");

        operationTable.setContainerDataSource(operationContainer);
        operationTable.setVisibleColumns(COLUMN_NAME, COLUMN_STATUS);

        operationTable.setColumnHeader(COLUMN_NAME, "Name");
        operationTable.setColumnHeader(COLUMN_STATUS, "Active");

        VaadinReactor.tableClickPublisher(operationTable)
                .map(itemClickEvent -> itemClickEvent.getItemId())
                .filter(itemId -> itemId != null)
                .cast(OperationDTO.class)
                .map(operation -> operation.getId())
                .subscribe(operationId -> navigateToOperation(operationId));
    }

    private Layout createTopToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addComponent(createNewOperationButton());
        toolbar.addComponent(createRefreshButton());
        return toolbar;
    }

    private Button createNewOperationButton() {
        newOperationButton = new Button("New operation");
        return newOperationButton;
    }

    private Button createRefreshButton() {
        refreshButton = new Button("Refresh");
        return refreshButton;
    }

    private void navigateToOperation(BigInteger operationId) {
        UI.getCurrent().getNavigator().navigateTo(OperationListView.VIEW_NAME + "/" + operationId);
    }
}
