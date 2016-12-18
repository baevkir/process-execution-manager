package com.pem.ui.presentation.grid;

import com.pem.model.operation.common.OperationDTO;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;

import java.util.List;

public class OperationGrid extends HorizontalLayout {

    private Grid operationGrid = new Grid();

    public OperationGrid() {
        operationGrid.addColumn("Name", String.class);
        operationGrid.addColumn("Active", Boolean.class);
        addComponent(operationGrid);
    }

    public void load(List<OperationDTO> operations) {
        for (OperationDTO operation : operations) {
            operationGrid.addRow(operation.getName(), operation.isActive());
        }
    }
}
