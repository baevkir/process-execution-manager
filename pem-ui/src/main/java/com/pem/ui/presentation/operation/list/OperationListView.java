package com.pem.ui.presentation.operation.list;

import com.pem.ui.presentation.operation.view.BaseOperationView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import io.reactivex.Observable;


public interface OperationListView extends View {
    String VIEW_NAME = "operations";
    void openOperation(BaseOperationView operationView);
    Observable<ViewChangeListener.ViewChangeEvent> getViewObservable();
    OperationList getOperationList();
}
