package com.pem.ui.presentation.process;

import com.pem.model.proccess.ExecutionProcessObject;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;
import java.util.List;

@UIScope
@SpringComponent
public class ProcessesList extends HorizontalLayout {

    private static final String COLUMN_NAME = "name";

    private boolean dataLoaded;
    private final Table processesTable = new Table();
    private final BeanItemContainer<ExecutionProcessObject> processesContainer = new BeanItemContainer<>(ExecutionProcessObject.class);

    public void load(List<ExecutionProcessObject> process) {
        processesContainer.removeAllItems();
        processesContainer.addAll(process);
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

        mainLayout.addComponent(processesTable);
        mainLayout.setExpandRatio(processesTable, 1.0f);

        processesTable.setSelectable(true);
        processesTable.setHeight("100%");
        processesTable.setWidth("250px");

        processesTable.setContainerDataSource(processesContainer);
        processesTable.setVisibleColumns(COLUMN_NAME);

        processesTable.setColumnHeader(COLUMN_NAME, "Name");

        addSelectionListener();
    }

    private void addSelectionListener() {
        processesTable.addListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                ExecutionProcessObject process = (ExecutionProcessObject) event.getItemId();
                UI.getCurrent().getNavigator().navigateTo(ProcessMainView.VIEW_NAME + "/" + process.getId());
            }
        });
    }
}
