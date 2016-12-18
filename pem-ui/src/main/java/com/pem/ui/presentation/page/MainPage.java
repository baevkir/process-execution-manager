package com.pem.ui.presentation.page;

import com.pem.logic.service.operation.OperationService;
import com.pem.ui.presentation.grid.OperationGrid;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("pem")
@SpringUI
public class MainPage extends UI {

    private OperationService operationService;
    private OperationGrid operationsTable = new OperationGrid();

    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(operationsTable);
        setContent(layout);

        operationsTable.load(operationService.getAllOperations());
    }

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public void setOperationService(OperationService operationService) {
        this.operationService = operationService;
    }
}
