package com.pem.ui.presentation.operation.list;

import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.rx.RxVaadin;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import io.reactivex.Observable;

import javax.annotation.PostConstruct;
import java.util.List;

@UIScope
@SpringComponent
public class OperationList extends HorizontalLayout{

    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_STATUS = "active";

    private boolean dataLoaded;
    private Observable<ItemClickEvent> selectObservable;
    private Observable<Button.ClickEvent> createButtonObservable;
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

    public Observable<ItemClickEvent> getSelectObservable() {
        return selectObservable;
    }

    public Observable<Button.ClickEvent> getCreateButtonObservable() {
        return createButtonObservable;
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

        initSelectionObserver();
    }

    private void initSelectionObserver() {
        selectObservable = RxVaadin.tableClickObservable(operationTable);
    }

    private Layout createTopToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addComponent(createNewOperationButton());

        return toolbar;
    }

    private Button createNewOperationButton() {
        Button newOperation = new Button("New operation");
        createButtonObservable = RxVaadin.buttonClickObservable(newOperation);
        return newOperation;
    }
}
