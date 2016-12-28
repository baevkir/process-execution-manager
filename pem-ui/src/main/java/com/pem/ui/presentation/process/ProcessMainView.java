package com.pem.ui.presentation.process;

import com.pem.model.proccess.ExecutionProcessDTO;
import com.vaadin.navigator.View;

import java.util.List;

public interface ProcessMainView extends View {

    String VIEW_NAME = "process-view";

    void load(List<ExecutionProcessDTO> process);
}
