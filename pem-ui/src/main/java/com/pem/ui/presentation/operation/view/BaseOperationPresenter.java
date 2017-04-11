package com.pem.ui.presentation.operation.view;

import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationObject;
import com.pem.ui.presentation.common.presenter.BaseBeanPresenter;
import com.pem.ui.presentation.common.reactor.VaadinReactor;
import com.pem.ui.presentation.operation.list.OperationListView;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class BaseOperationPresenter<O extends OperationObject, V extends BaseOperationView<O>> extends BaseBeanPresenter<O, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseOperationPresenter.class);

    @Autowired
    private OperationService operationService;

    @Override
    public Mono<BeanFieldGroup<O>> bindBean(O bean) {
        return super.bindBean(bean).doOnNext(binder -> initSavePublisher(binder));
    }

    private void initSavePublisher(BeanFieldGroup<O> sourceBinder) {
        VaadinReactor.binderPostCommitPublisher(sourceBinder)
                .map(commitEvent -> sourceBinder)
                .map(binder -> binder.getItemDataSource().getBean())
                .flatMap(operation -> {
                    if (operation.getId() == null) {
                        return operationService.createOperation(operation);
                    }
                    return operationService.updateOperation(operation)
                            .then(() -> Mono.just(operation))
                            .doOnNext(currentOperation -> LOGGER.debug("Operation {} updated.", currentOperation));
                }).subscribe(operation -> navigateToOperation(operation.getId()));
    }

    private void navigateToOperation(BigInteger operationId) {
        UI.getCurrent().getNavigator().navigateTo(OperationListView.VIEW_NAME + "/" + operationId);
    }

}
