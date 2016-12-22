package com.pem.ui.presentation.operation.list;

import com.google.common.eventbus.EventBus;
import com.pem.model.operation.common.OperationDTO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
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
        operationContainer.addAll(operations);
        dataLoaded = true;
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    @PostConstruct
    public void init() {
        dataLoaded = false;
        addComponent(operationTable);

        operationTable.setSelectable(true);
        operationTable.setHeight("100%");
        operationTable.setHeight("20%");

        operationTable.setContainerDataSource(operationContainer);
        operationTable.setVisibleColumns("name", "active");

        operationTable.setColumnHeader("name", "Name");
        operationTable.setColumnHeader("active", "Active");
    }
}
