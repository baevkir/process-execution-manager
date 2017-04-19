package com.pem.ui.presentation.process;

import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.ui.presentation.common.navigator.NavigationConst;
import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.common.navigator.NavigationManager;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@UIScope
@SpringComponent
public class ProcessesList extends HorizontalLayout {

    private static final String COLUMN_NAME = "name";

    private boolean dataLoaded;
    private final Table processesTable = new Table();
    private final BeanItemContainer<ExecutionProcessObject> processesContainer = new BeanItemContainer<>(ExecutionProcessObject.class);

    @Autowired
    private NavigationManager navigator;

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
        processesTable.addListener((ItemClickEvent.ItemClickListener) event -> {
            ExecutionProcessObject process = (ExecutionProcessObject) event.getItemId();
            NavigationParams params = NavigationParams.builder()
                    .setViewName(ProcessMainView.VIEW_NAME)
                    .addUrlParam(NavigationConst.ID_PARAM, process.getId().toString())
                    .build();
            navigator.navigate(params);
        });
    }
}
