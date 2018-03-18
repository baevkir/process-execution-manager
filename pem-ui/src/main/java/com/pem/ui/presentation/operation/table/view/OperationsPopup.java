package com.pem.ui.presentation.operation.table.view;

import com.pem.model.operation.common.OperationObject;
import reactor.core.publisher.Mono;

public interface OperationsPopup {
    <O extends OperationObject> Mono<O> openOperation( O operation);
}
