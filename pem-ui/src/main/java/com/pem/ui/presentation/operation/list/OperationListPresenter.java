package com.pem.ui.presentation.operation.list;

import com.pem.logic.service.operation.OperationService;
import com.pem.logic.service.process.ExecutionProcessService;
import com.pem.model.operation.common.OperationObject;
import com.pem.ui.presentation.common.navigator.NavigationConst;
import com.pem.ui.presentation.common.navigator.NavigationManager;
import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.common.presenter.BasePresenter;
import com.pem.ui.presentation.common.view.provider.OperationViewObject;
import com.pem.ui.presentation.common.view.provider.PemViewProvider;
import com.pem.ui.presentation.operation.view.BaseOperationView;
import com.pem.ui.presentation.process.ProcessMainView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.UI;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

@SpringComponent
@UIScope
public class OperationListPresenter extends BasePresenter<OperationListView> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationListPresenter.class);

    private NavigationManager navigator;
    private OperationService operationService;
    private ExecutionProcessService processService;
    private PemViewProvider viewProvider;
    private ChooseOperationTypeWindow chooseOperationTypeWindow;

    @Override
    public void bind(OperationListView view) {
        super.bind(view);
        Flux<NavigationParams> eventPublisher = navigator.onNavigationChange()
                .filter(params -> params.getViewName().equals(OperationListView.VIEW_NAME));

        OperationList operationList = getView().getOperationList();
        eventPublisher.next()
                .cast(Object.class)
                .mergeWith(operationList.getRefreshPublisher())
                .subscribe(eventObject -> reloadOperations());

        openOperations(eventPublisher);
        createProcess(eventPublisher);
    }

    public void reloadOperations() {
        getView().getOperationList().load(operationService.getAllOperations());
    }

    protected void openOperations(Flux<NavigationParams> eventPublisher) {
        Flux<String> operationPublisher = eventPublisher
                .filter(params -> params.hasUrlParam(NavigationConst.ID_PARAM))
                .map(params -> params.getUrlParam(NavigationConst.ID_PARAM).get())
                .doOnNext(parameter -> Assert.isTrue(StringUtils.isNotEmpty(parameter), "Wrong ID param."));

        AtomicReference<OperationObject> operationReference = new AtomicReference<>();
        operationPublisher.filter(parameter -> StringUtils.isNumeric(parameter))
                .map(parameter -> new BigInteger(parameter))
                .flatMap(operationId -> operationService.getOperation(operationId))
                .mergeWith(getNewOperationPublisher(operationPublisher))

                .doOnNext(operation -> LOGGER.debug("Open operation: {}", operation))
                .doOnNext(operation -> operationReference.set(operation))
                .map(operation -> viewProvider.getView(operation.getClass()))
                .doOnNext(operationView -> Assert.notNull(operationView, "Cannot find view for operation"))
                .cast(BaseOperationView.class)
                .subscribe(operationView -> bindToForm(operationReference.get(), operationView));
    }

    protected void createProcess(Flux<NavigationParams> eventPublisher) {
        eventPublisher.filter(params -> params.hasUrlParam(NavigationConst.CREATE_PROCCESS_PARAM))
                .map(params -> params.getUrlParam(NavigationConst.CREATE_PROCCESS_PARAM).get())
                .doOnNext(parameter -> Assert.isTrue(StringUtils.isNotEmpty(parameter), "Wrong param for creation process"))
                .doOnNext(parameter -> Assert.isTrue(StringUtils.isNumeric(parameter), "Wrong param for creation process"))
                .map(parameter -> new BigInteger(parameter))
                .flatMap(operationId -> operationService.getOperation(operationId))
                .flatMap(operation -> processService.createExecutionProcess(operation))
                .subscribe(executionProcess ->
                        navigator.navigate(NavigationParams.builder()
                        .setViewName(ProcessMainView.VIEW_NAME)
                        .addUrlParam(NavigationConst.ID_PARAM, String.valueOf(executionProcess.getId()))
                        .build()));
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

    private <O extends OperationObject> void bindToForm(O source, BaseOperationView<O> operationForm) {
        operationForm.bind(source)
                .doOnSuccess(signal -> getView().openOperation(operationForm))
                .subscribe();
    }

    private Mono<OperationViewObject> openChooseOperationTypeWindow() {
        return Mono.just(chooseOperationTypeWindow)
                .doOnSuccess(window -> UI.getCurrent().addWindow(window))
                .doOnSuccess(window -> window.setVisible(true))
                .flatMap(window -> window.getPublisher())
                .single();
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
    public void setProcessService(ExecutionProcessService processService) {
        this.processService = processService;
    }

    @Autowired
    public void setViewProvider(PemViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }

    @Autowired
    public void setChooseOperationTypeWindow(ChooseOperationTypeWindow chooseOperationTypeWindow) {
        this.chooseOperationTypeWindow = chooseOperationTypeWindow;
    }

}