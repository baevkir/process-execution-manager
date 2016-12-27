package com.pem.ui.presentation.operation.list;

import com.pem.ui.presentation.common.view.BindForm;
import com.vaadin.navigator.View;

public interface OperationListView extends View, OperationsLoader {
    String VIEW_NAME = "operations";
    void openOperation(BindForm operationView);
}
