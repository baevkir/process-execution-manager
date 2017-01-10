package com.pem.ui.presentation.process;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import io.reactivex.Observable;


public interface ProcessMainView extends View {
    String VIEW_NAME = "processes";
    Observable<ViewChangeListener.ViewChangeEvent> getMainViewObservable();
    ProcessesList getProcessesList();
}
