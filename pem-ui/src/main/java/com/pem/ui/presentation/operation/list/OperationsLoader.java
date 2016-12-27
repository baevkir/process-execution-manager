package com.pem.ui.presentation.operation.list;

import com.pem.model.operation.common.OperationDTO;

import java.util.List;

public interface OperationsLoader {
    void load(List<OperationDTO> operations);
}
