package com.pem.ui.presentation.process;

import com.pem.logic.service.process.ExecutionProcessService;
import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.ui.presentation.common.navigator.NavigationConst;
import com.pem.ui.presentation.common.navigator.NavigationManager;
import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.common.presenter.BasePresenter;
import com.pem.ui.presentation.common.view.provider.PemViewProvider;
import com.pem.ui.presentation.process.form.ProcessFormView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.math.BigInteger;

@SpringComponent
@UIScope
public class ProcessMainPresenter extends BasePresenter<ProcessMainView> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessMainPresenter.class);
    private ExecutionProcessService processService;
    private PemViewProvider viewProvider;
    private NavigationManager navigator;
    private ProcessesList processesList;

    @Override
    public void bind(ProcessMainView view) {
        super.bind(view);
        Flux<NavigationParams> eventPublisher = navigator.onNavigationChange()
                .filter(params -> params.getViewName().equals(ProcessMainView.VIEW_NAME));

        eventPublisher.next()
                .subscribe(params -> reloadProcesses());

        addOpenProcessObserver(eventPublisher);
        addRunProcessObserver(eventPublisher);
    }

    private void reloadProcesses() {
        processesList.load(processService.getAllExecutionProcesses());
    }

    private void addOpenProcessObserver(Flux<NavigationParams> eventPublisher) {
        getProcessFromNavigationParams(eventPublisher, NavigationConst.ID_PARAM)
                .doOnNext(process -> LOGGER.trace("Open process: {}.", process))

                .map(process -> Tuples.of(process, viewProvider.getView(process.getClass())))
                .doOnNext(processTuple -> Assert.notNull(processTuple.getT2(), "Cannot find view for operation"))
                .doOnNext(processTuple -> Assert.isInstanceOf(ProcessFormView.class, processTuple.getT2(), "View for operation is not BaseOperationView"))
                .subscribe(processTuple -> bindToForm(processTuple.getT1(), (ProcessFormView) processTuple.getT2()));
    }

    private void addRunProcessObserver(Flux<NavigationParams> eventPublisher) {
        getProcessFromNavigationParams(eventPublisher, NavigationConst.RUN_PROCESS_PARAM)
                .flatMap(processObject -> processService.executeProcess(processObject))
                .subscribe();
    }

    private Flux<ExecutionProcessObject> getProcessFromNavigationParams(Flux<NavigationParams> eventPublisher, String parameterName) {
        return eventPublisher.filter(params -> params.hasUrlParam(parameterName))
                .map(params -> params.getUrlParam(parameterName).get())
                .doOnNext(parameter -> Assert.isTrue(StringUtils.isNotEmpty(parameter), "Wrong ID param."))
                .doOnNext(parameter -> Assert.isTrue(StringUtils.isNumeric(parameter), "Wrong ID param."))

                .map(parameter -> new BigInteger(parameter))
                .flatMap(processId -> processService.getExecutionProcess(processId));
    }

    private void bindToForm(ExecutionProcessObject source, ProcessFormView operationForm) {
        operationForm.bind(source)
                .doOnSuccess(signal -> getView().openProcess(operationForm))
                .subscribe();
    }
    @Autowired
    public void setProcessService(ExecutionProcessService processService) {
        this.processService = processService;
    }

    @Autowired
    public void setNavigator(NavigationManager navigator) {
        this.navigator = navigator;
    }

    @Autowired
    public void setProcessesList(ProcessesList processesList) {
        this.processesList = processesList;
    }

    @Autowired
    public void setViewProvider(PemViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }
}
