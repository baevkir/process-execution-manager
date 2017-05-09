package com.pem.ui.presentation.process;

import com.pem.ui.presentation.process.form.ProcessFormView;
import com.vaadin.navigator.View;


public interface ProcessMainView extends View {
    String VIEW_NAME = "processes";
    void openProcess(ProcessFormView operationView);
}
