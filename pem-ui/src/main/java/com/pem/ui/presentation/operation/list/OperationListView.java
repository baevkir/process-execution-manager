package com.pem.ui.presentation.operation.list;

import com.pem.model.operation.common.OperationDTO;
import com.vaadin.navigator.View;
import com.vaadin.ui.AbstractComponent;

import java.util.List;

public interface OperationListView extends View {
    String VIEW_NAME = "operations";

    void loadOperations(List<OperationDTO> operations);
    void openOperation(AbstractComponent operationView);
}
