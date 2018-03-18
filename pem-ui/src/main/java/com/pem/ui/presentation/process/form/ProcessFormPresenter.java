package com.pem.ui.presentation.process.form;

//@SpringComponent
//@ViewScope
public class ProcessFormPresenter {
//        extends BaseBeanPresenter<ExecutionProcessObject, ProcessFormView> {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseOperationPresenter.class);
//    private ExecutionProcessService processService;
//
//    @Override
//    public Mono<PemBeanFieldGroup<ExecutionProcessObject>> bindBean(ExecutionProcessObject sourceBean) {
//        return super.bindBean(sourceBean)
//                .doOnSuccess(binder -> registerRunProcessPublisher(binder));
//    }
//
//    private void registerRunProcessPublisher(PemBeanFieldGroup<ExecutionProcessObject> binder) {
//        getView().getRunProcessPublisher()
//                .doOnNext(clickEvent -> binder.commitUnchecked())
//                .map(clickEvent -> binder.getItemDataSource().getBean())
//                .doOnNext(process -> LOGGER.debug("Start to execute Process {}.", process))
//                .flatMap(process -> processService.executeProcess(process))
//                .doOnError(throwable -> {throw new RuntimeException(throwable);})
//                .subscribe();
//    }
//
//    @Autowired
//    public void setProcessService(ExecutionProcessService processService) {
//        this.processService = processService;
//    }


}
