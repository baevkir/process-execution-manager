package com.pem.ui.presentation.operation.table.presenter;

import com.pem.core.common.annotation.spring.PrototypeComponent;
import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationObject;
import com.pem.ui.common.PublisherDataProvider;
import com.pem.ui.presentation.common.navigator.NavigationConst;
import com.pem.ui.presentation.common.navigator.NavigationManager;
import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.common.presenter.AbstractPresenter;
import com.pem.ui.presentation.operation.list.OperationListView;
import com.pem.ui.presentation.operation.table.view.OperationsPopup;
import com.pem.ui.presentation.operation.table.view.OperationsTableView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.math.BigInteger;

@PrototypeComponent
public class OperationTablePresenterImpl extends AbstractPresenter<OperationsTableView> implements OperationsTablePresenter {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationTablePresenterImpl.class);

    private NavigationManager navigator;
    private OperationService operationService;
    private PublisherDataProvider<OperationObject> operationDataProvider;
    private OperationsPopup operationsPopup;

    @PostConstruct
    void init() {
        Flux<NavigationParams> eventPublisher = navigator.onNavigationChange()
                .filter(params -> params.getViewName().equals(OperationListView.VIEW_NAME));

        Mono<NavigationParams> firstNavigation = eventPublisher.next()
                .doOnNext(params -> initDataProvider()).subscribe();

        openOperations(eventPublisher);
    }

    private void openOperations(Flux<NavigationParams> eventPublisher) {
        Flux<String> operationPublisher = eventPublisher
                .filter(params -> params.hasUrlParam(NavigationConst.ID_PARAM))
                .map(params -> params.getUrlParam(NavigationConst.ID_PARAM).get())
                .doOnNext(parameter -> Assert.isTrue(StringUtils.isNotEmpty(parameter), "Wrong ID param."));

        operationPublisher
                .filter(parameter -> StringUtils.isNumeric(parameter))
                .map(parameter -> new BigInteger(parameter))
                .flatMap(operationId -> operationService.getOperation(operationId))
                .doOnNext(operation -> LOGGER.debug("Open operation: {}", operation))
                .flatMap(operation -> operationsPopup.openOperation(operation)).single()
                .doOnNext(operation -> saveOperation(operation))
                .subscribe(operation -> operationDataProvider.reloadData());
    }

    private void initDataProvider() {
        operationDataProvider = PublisherDataProvider.<OperationObject>builder()
                .setDataLoader(() -> operationService.getAllOperations())
                .build();
        getView().setOperationsProvider(operationDataProvider);
    }

    private <O extends OperationObject> void saveOperation(O operation) {
        if (operation.getId() == null) {
            operationService.createOperation(operation);
            return;
        }

        operationService.updateOperation(operation);
    }
    @Autowired
    public void setNavigator(NavigationManager navigator) {
        this.navigator = navigator;
    }
    @Autowired
    public void setOperationService(OperationService operationService) {
        this.operationService = operationService;
    }
    @Autowired
    public void setOperationsPopup(OperationsPopup operationsPopup) {
        this.operationsPopup = operationsPopup;
    }
}
