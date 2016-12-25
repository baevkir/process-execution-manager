package com.pem.ui.presentation.operation.list;

import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.view.BindForm;
import com.vaadin.navigator.View;

import java.util.List;

public interface OperationListView extends View {
    String VIEW_NAME = "operations";

    void loadOperations(List<OperationDTO> operations);
    void openOperation(BindForm operationView);
}
