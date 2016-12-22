package com.pem.ui.presentation.operation.list;

import com.google.common.eventbus.EventBus;
import com.pem.model.operation.common.OperationDTO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
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
    void init() {
        dataLoaded = false;
        addComponent(operationTable);

        operationTable.setSelectable(true);
        operationTable.setHeight("100%");
        operationTable.setHeight("20%");

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
}
