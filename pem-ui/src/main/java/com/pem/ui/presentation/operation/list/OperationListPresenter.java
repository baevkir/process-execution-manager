package com.pem.ui.presentation.operation.list;

import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationObject;
import com.pem.ui.presentation.common.navigator.NavigationConst;
import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.common.navigator.NavigationManager;
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
    private NavigationManager navigator;

    @Autowired
    private OperationService operationService;

    @Autowired
    private PemViewProvider viewProvider;

    @Autowired
    private ChooseOperationTypeWindow chooseOperationTypeWindow;

    private boolean isDataLoaded = false;

    @Override
    public void bind(OperationListView view) {
        super.bind(view);
        Flux<NavigationParams> eventPublisher = navigator.onNavigationChange()
                .filter(params -> params.getViewName().equals(OperationListView.VIEW_NAME));

        OperationList operationList = getView().getOperationList();
        eventPublisher.filter(event -> !isDataLoaded)
                .cast(Object.class)
                .mergeWith(operationList.getRefreshPublisher())
                .subscribe(eventObject -> reloadOperations());


        Flux<String> operationPublisher = eventPublisher
                .map(params -> params.getUrlParam(NavigationConst.ID_PARAM).orElse(""))
                .filter(params -> StringUtils.isNotEmpty(params));

        operationPublisher.filter(parameter -> StringUtils.isNumeric(parameter))
                .map(parameter -> new BigInteger(parameter))
                .flatMap(operationId -> operationService.getOperation(operationId))
                .mergeWith(getNewOperationPublisher(operationPublisher))
                .subscribe(operation -> openOperationForm(operation));
    }

    public void reloadOperations(){
        isDataLoaded = true;
        getView().getOperationList().load(operationService.getAllOperations());
    }

    private Flux<OperationObject> getNewOperationPublisher(Flux<String> operationPublisher) {
        return operationPublisher
                .filter(parameter -> parameter.equals(NavigationConst.NEW_OBJECT_VALUE))
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