package com.pem.ui.presentation.process.form;

import com.pem.logic.service.process.ExecutionProcessService;
import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.ui.common.binder.PemBeanFieldGroup;
import com.pem.ui.presentation.common.presenter.BaseBeanPresenter;
import com.pem.ui.presentation.operation.view.BaseOperationPresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

@SpringComponent
@ViewScope
public class ProcessFormPresenter extends BaseBeanPresenter<ExecutionProcessObject, ProcessFormView> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseOperationPresenter.class);
    private ExecutionProcessService processService;

    @Override
    public Mono<PemBeanFieldGroup<ExecutionProcessObject>> bindBean(ExecutionProcessObject sourceBean) {
        return super.bindBean(sourceBean)
                .doOnSuccess(binder -> registerRunProcessPublisher(binder));
    }

    private void registerRunProcessPublisher(PemBeanFieldGroup<ExecutionProcessObject> binder) {
        getView().getRunProcessPublisher()
                .doOnNext(clickEvent -> binder.commitUnchecked())
                .map(clickEvent -> binder.getItemDataSource().getBean())
                .doOnNext(process -> LOGGER.debug("Start to execute Process {}.", process))
                .flatMap(process -> processService.executeProcess(process))
                .doOnError(throwable -> {throw new RuntimeException(throwable);})
                .subscribe();
    }

    @Autowired
    public void setProcessService(ExecutionProcessService processService) {
        this.processService = processService;
    }


}
