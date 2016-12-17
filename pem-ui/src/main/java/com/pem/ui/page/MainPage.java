package com.pem.ui.page;

import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationDTO;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Theme("valo")
@SpringUI
public class MainPage extends UI {

    private OperationService operationService;

    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        layout.addComponent(new Label("Hello! Current Operations list:"));

        List<OperationDTO> operations = operationService.getAllOperations();
        for (OperationDTO operation : operations) {
            layout.addComponent(new Label(operation.getName() + ". Active: " + operation.isActive()));
        }
    }

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public void setOperationService(OperationService operationService) {
        this.operationService = operationService;
    }
}
