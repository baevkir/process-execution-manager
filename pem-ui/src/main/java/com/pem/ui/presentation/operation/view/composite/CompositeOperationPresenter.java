package com.pem.ui.presentation.operation.view.composite;

import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.ui.common.binder.PemBeanFieldGroup;
import com.pem.ui.presentation.operation.view.BaseOperationPresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringComponent
@ViewScope
public class CompositeOperationPresenter extends BaseOperationPresenter<SyncCompositeOperationDTO, CompositeOperationView> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompositeOperationPresenter.class);

    @Autowired
    private OperationService operationService;

    @Override
    public Mono<PemBeanFieldGroup<SyncCompositeOperationDTO>> bindBean(SyncCompositeOperationDTO bean) {
        return getView().load(getAllOperationsExceptCurrent(bean))
                .then(super.bindBean(bean));
    }

    private Flux<OperationObject> getAllOperationsExceptCurrent(SyncCompositeOperationDTO currentOperation) {
        return operationService.getAllOperations()
                .filter(operation -> !operation.getId().equals(currentOperation.getId()));
    }

}
