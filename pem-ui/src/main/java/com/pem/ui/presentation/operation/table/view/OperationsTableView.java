package com.pem.ui.presentation.operation.table.view;

import com.pem.model.operation.common.OperationObject;
import com.pem.ui.common.PublisherDataProvider;
import com.vaadin.navigator.View;

public interface OperationsTableView extends View {
    String VIEW_NAME = "operations";
    void setOperationsProvider(PublisherDataProvider<OperationObject> dataProvider);
}
