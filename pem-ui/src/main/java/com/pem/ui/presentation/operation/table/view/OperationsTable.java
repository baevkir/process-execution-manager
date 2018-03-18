package com.pem.ui.presentation.operation.table.view;


import com.pem.core.common.annotation.spring.PrototypeComponent;
import com.pem.model.operation.common.OperationObject;
import com.pem.ui.common.PublisherDataProvider;
import com.pem.ui.common.reactor.VaadinReactor;
import com.pem.ui.presentation.common.navigator.NavigationConst;
import com.pem.ui.presentation.common.navigator.NavigationManager;
import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.operation.list.OperationListView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


@PrototypeComponent
public class OperationsTable extends HorizontalLayout {
    private NavigationManager navigator;
    private final Grid<OperationObject> operationsGrid = new Grid<>();
    private ButtonRenderer<OperationObject> editButtonRenderer = new ButtonRenderer<>();

    public void setDataProvider(PublisherDataProvider<OperationObject> dataProvider) {
        operationsGrid.setDataProvider(dataProvider);
    }

    @PostConstruct
    void init() {
        operationsGrid.addColumn(operation -> operation.isActive()).setCaption("Active").setMaximumWidth(90);
        operationsGrid.addColumn(operation -> operation.getName()).setCaption("Name");

        operationsGrid.addColumn(operation -> operation.getCreatedWhen()).setCaption("Created");
        operationsGrid.addColumn(operationObject -> "Edit", editButtonRenderer);
        subscribeEditButton();

        operationsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        operationsGrid.setSizeFull();

        addComponentsAndExpand(operationsGrid);
    }

    private void subscribeEditButton() {
        VaadinReactor.clickableRendererPublisher(editButtonRenderer)
                .map(event -> NavigationParams.builder()
                        .setViewName(OperationListView.VIEW_NAME)
                        .addUrlParam(NavigationConst.ID_PARAM, event.getItem().getId().toString())
                        .build())
                .subscribe(params -> navigator.navigate(params));
    }

    @Autowired
    public void setNavigator(NavigationManager navigator) {
        this.navigator = navigator;
    }
}
