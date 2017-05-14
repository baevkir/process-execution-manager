package com.pem.ui.presentation.operation.view;

import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationObject;
import com.pem.ui.common.binder.PemBeanFieldGroup;
import com.pem.ui.presentation.common.navigator.NavigationConst;
import com.pem.ui.presentation.common.navigator.NavigationManager;
import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.common.presenter.BaseBeanPresenter;
import com.pem.ui.presentation.common.reactor.VaadinReactor;
import com.pem.ui.presentation.operation.list.OperationListPresenter;
import com.pem.ui.presentation.operation.list.OperationListView;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public class BaseOperationPresenter<O extends OperationObject, V extends BaseOperationView<O>> extends BaseBeanPresenter<O, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseOperationPresenter.class);

    private OperationService operationService;

    private OperationListPresenter operationListPresenter;

    private NavigationManager navigator;

    @Override
    public Mono<PemBeanFieldGroup<O>> bindBean(O bean) {
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
                }).subscribe(operation -> {
                    navigateToOperation(operation.getId());
                    operationListPresenter.reloadOperations();
                });
    }

    private void navigateToOperation(BigInteger operationId) {
        Assert.notNull(operationId, "Cannot navigate. Operation is NULL");
        NavigationParams params = NavigationParams.builder()
                .setViewName(OperationListView.VIEW_NAME)
                .addUrlParam(NavigationConst.ID_PARAM, operationId.toString())
                .build();
        navigator.navigate(params);
    }

    @Autowired
    public void setOperationService(OperationService operationService) {
        this.operationService = operationService;
    }

    @Autowired
    public void setOperationListPresenter(OperationListPresenter operationListPresenter) {
        this.operationListPresenter = operationListPresenter;
    }

    @Autowired
    public void setNavigator(NavigationManager navigator) {
        this.navigator = navigator;
    }
}
