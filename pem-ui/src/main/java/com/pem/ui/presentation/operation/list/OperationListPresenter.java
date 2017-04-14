package com.pem.ui.presentation.operation.list;

import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationObject;
import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.common.navigator.UINavigator;
import com.pem.ui.presentation.common.presenter.BasePresenter;
import com.pem.ui.presentation.common.view.provider.OperationViewObject;
import com.pem.ui.presentation.common.view.provider.PemViewProvider;
import com.pem.ui.presentation.operation.view.BaseOperationView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@SpringComponent
@UIScope
public class OperationListPresenter extends BasePresenter<OperationListView> {

    @Autowired
    private UINavigator navigator;

    @Autowired
    private OperationService operationService;

    @Autowired
    private PemViewProvider viewProvider;

    @Autowired
    private ChooseOperationTypeWindow chooseOperationTypeWindow;

    @Override
    public void bind(OperationListView view) {
        super.bind(view);
        Flux<NavigationParams> eventPublisher = navigator.onViewChange()
                .filter(params -> params.getViewName().equals(OperationListView.VIEW_NAME));

        OperationList operationList = getView().getOperationList();
        eventPublisher
                .filter(event -> !operationList.isDataLoaded() || event.hasUrlParam(NavigationParams.REFRESH_LIST_PARAM))
                .cast(Object.class)
                .mergeWith(operationList.getRefreshPublisher())
                .map(eventObject -> operationService.getAllOperations())
                .subscribe(operationFlux -> operationList.load(operationFlux));

        getOperationPublisher(eventPublisher)
                .mergeWith(getNewOperationPublisher())
                .subscribe(operation -> openOperationForm(operation));
    }

    private Flux<OperationObject> getOperationPublisher(Flux<NavigationParams> eventPublisher) {
        return eventPublisher
                .map(params -> params.getUrlParam(NavigationParams.ID_PARAM).orElse(""))
                .filter(parameters -> StringUtils.isNotEmpty(parameters))
                .filter(parameters -> StringUtils.isNumeric(parameters))
                .map(parameters -> new BigInteger(parameters))
                .flatMap(operationId -> operationService.getOperation(operationId));
    }

    private Flux<OperationObject> getNewOperationPublisher() {
        return getView().getOperationList().getNewOperationPublisher()
                .flatMap(clickEvent -> openChooseOperationTypeWindow())
                .map(operationViewObject -> operationViewObject.getOperationType())
                .map(operationType -> newOperation(operationType));
    }

    private OperationObject newOperation(Class<? extends OperationObject> operationType) {
        Assert.notNull(operationType);
        try {
            return operationType.newInstance();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void openOperationForm(OperationObject source) {
        Mono.just(source)
                .map(operation -> viewProvider.getView(operation.getClass()))
                .doOnNext(operationView -> Assert.notNull(operationView))
                .cast(BaseOperationView.class)
                .subscribe(operationForm -> bindToForm(source, operationForm));
    }

    private <O extends OperationObject> void bindToForm(O source, BaseOperationView<O> operationForm) {
        operationForm.bind(source)
                .doOnSuccess(signal -> getView().openOperation(operationForm))
                .subscribe();
    }

    private Mono<OperationViewObject> openChooseOperationTypeWindow() {

        return Mono.just(chooseOperationTypeWindow)
                .doOnSuccess(window -> UI.getCurrent().addWindow(window))
                .doOnSuccess(window -> window.setVisible(true))
                .flatMap(window -> window.getPublisher()).single();
    }
}