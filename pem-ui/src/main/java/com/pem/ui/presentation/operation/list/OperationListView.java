package com.pem.ui.presentation.operation.list;

import com.pem.model.operation.common.OperationDTO;
import com.vaadin.navigator.View;

import java.util.List;

public interface OperationListView extends View {
    void loadOperations(List<OperationDTO> operations);
}
