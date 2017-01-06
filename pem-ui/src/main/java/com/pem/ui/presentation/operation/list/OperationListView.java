package com.pem.ui.presentation.operation.list;

import com.pem.ui.presentation.common.view.BindForm;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import rx.subjects.PublishSubject;

public interface OperationListView extends View {
    String VIEW_NAME = "operations";
    void openOperation(BindForm operationView);
    PublishSubject<ViewChangeListener.ViewChangeEvent> getViewSubject();
    OperationList getOperationList();
}
