package com.pem.ui.presentation.operation.list;

import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.presenter.BasePresenter;
import com.pem.ui.presentation.common.view.provider.OperationViewObject;
import com.pem.ui.presentation.common.view.provider.PemViewProvider;
import com.pem.ui.presentation.operation.view.BaseOperationView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.EventObject;

@SpringComponent
@UIScope
public class OperationListPresenter extends BasePresenter<OperationListView> {

    @Autowired
    private OperationService operationService;

    @Autowired
    private PemViewProvider viewProvider;

    @Autowired
    private ChooseOperationTypeWindow chooseOperationTypeWindow;

    @Override
    public void bind(OperationListView view) {
        super.bind(view);
        Flux<ViewChangeListener.ViewChangeEvent> eventPublisher = view.getViewChangePublisher();

        OperationList operationList = getView().getOperationList();
        eventPublisher.filter(event -> !operationList.isDataLoaded())
                .cast(EventObject.class)
                .concatWith(operationList.getRefreshPublisher())
                .map(eventObject -> operationService.getAllOperations())
                .subscribe(operationFlux -> operationList.load(operationFlux));

        getOperationPublisher(eventPublisher)
                .concatWith(getNewOperationPublisher())
                .subscribe(operation -> openOperationForm(operation));
    }

    private Flux<OperationDTO> getOperationPublisher(Flux<ViewChangeListener.ViewChangeEvent> eventPublisher) {
        return eventPublisher.map(event -> event.getParameters())
                .filter(parameters -> StringUtils.isNotEmpty(parameters))
                .filter(parameters -> StringUtils.isNumeric(parameters))
                .map(parameters -> new BigInteger(parameters))
                .flatMap(operationId -> operationService.getOperation(operationId));
    }

    private Flux<OperationDTO> getNewOperationPublisher() {
        return getView().getOperationList().getNewOperationPublisher()
                .flatMap(clickEvent -> openChooseOperationTypeWindow())
                .map(operationViewObject -> operationViewObject.getOperationType())
                .map(operationType -> newOperation(operationType));
    }

    private OperationDTO newOperation(Class<? extends OperationDTO> operationType) {
        Assert.notNull(operationType);
        try {
            return operationType.newInstance();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void openOperationForm(OperationDTO source) {
        Mono.just(source)
                .map(operation -> viewProvider.getView(operation.getClass()))
                .doOnNext(operationView -> Assert.notNull(operationView))
                .cast(BaseOperationView.class)
                .subscribe(operationForm -> bindToForm(source, operationForm));
    }

    private void bindToForm(OperationDTO source, BaseOperationView operationForm) {
        operationForm.bind(source)
                .doOnSuccess(signal -> getView().openOperation(operationForm))
                .subscribe();
    }
    private Mono<OperationViewObject> openChooseOperationTypeWindow() {
        return Mono.just(chooseOperationTypeWindow)
                .doOnNext(window -> UI.getCurrent().addWindow(window))
                .flatMap(window -> window.getPublisher())
                .next();
    }


}