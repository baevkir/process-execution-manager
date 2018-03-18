package com.pem.ui.presentation.process;

//@SpringComponent
//@UIScope
public class ProcessMainPresenter {
//        extends BasePresenter<ProcessMainView> {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessMainPresenter.class);
//    private ExecutionProcessService processService;
//    private PemViewProvider viewProvider;
//    private NavigationManager navigator;
//    private ProcessesList processesList;
//
//    @Override
//    public void bind(ProcessMainView view) {
//        super.bind(view);
//        Flux<NavigationParams> eventPublisher = navigator.onNavigationChange()
//                .filter(params -> params.getViewName().equals(ProcessMainView.VIEW_NAME));
//
//        eventPublisher.next()
//                .subscribe(params -> reloadProcesses());
//
//        addOpenProcessObserver(eventPublisher);
//        addRunProcessObserver(eventPublisher);
//    }
//
//    private void reloadProcesses() {
//        processesList.load(processService.getAllExecutionProcesses());
//    }
//
//    private void addOpenProcessObserver(Flux<NavigationParams> eventPublisher) {
//        getProcessFromNavigationParams(eventPublisher, NavigationConst.ID_PARAM)
//                .doOnNext(process -> LOGGER.trace("Open process: {}.", process))
//
//                .map(process -> Tuples.of(process, viewProvider.getView(process.getClass())))
//                .doOnNext(processTuple -> Assert.notNull(processTuple.getT2(), "Cannot find view for operation"))
//                .doOnNext(processTuple -> Assert.isInstanceOf(ProcessFormView.class, processTuple.getT2(), "View for operation is not BaseOperationView"))
//                .subscribe(processTuple -> bindToForm(processTuple.getT1(), (ProcessFormView) processTuple.getT2()));
//    }
//
//    private void addRunProcessObserver(Flux<NavigationParams> eventPublisher) {
//        getProcessFromNavigationParams(eventPublisher, NavigationConst.RUN_PROCESS_PARAM)
//                .flatMap(processObject -> processService.executeProcess(processObject))
//                .subscribe();
//    }
//
//    private Flux<ExecutionProcessObject> getProcessFromNavigationParams(Flux<NavigationParams> eventPublisher, String parameterName) {
//        return eventPublisher.filter(params -> params.hasUrlParam(parameterName))
//                .map(params -> params.getUrlParam(parameterName).get())
//                .doOnNext(parameter -> Assert.isTrue(StringUtils.isNotEmpty(parameter), "Wrong ID param."))
//                .doOnNext(parameter -> Assert.isTrue(StringUtils.isNumeric(parameter), "Wrong ID param."))
//
//                .map(parameter -> new BigInteger(parameter))
//                .flatMap(processId -> processService.getExecutionProcess(processId));
//    }
//
//    private void bindToForm(ExecutionProcessObject source, ProcessFormView operationForm) {
//        operationForm.bind(source)
//                .doOnSuccess(signal -> getView().openProcess(operationForm))
//                .subscribe();
//    }
//    @Autowired
//    public void setProcessService(ExecutionProcessService processService) {
//        this.processService = processService;
//    }
//
//    @Autowired
//    public void setNavigator(NavigationManager navigator) {
//        this.navigator = navigator;
//    }
//
//    @Autowired
//    public void setProcessesList(ProcessesList processesList) {
//        this.processesList = processesList;
//    }
//
//    @Autowired
//    public void setViewProvider(PemViewProvider viewProvider) {
//        this.viewProvider = viewProvider;
//    }
}
