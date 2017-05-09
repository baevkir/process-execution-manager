package com.pem.ui.presentation.process.form;

import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.ui.presentation.common.navigator.NavigationConst;
import com.pem.ui.presentation.common.navigator.NavigationManager;
import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.common.presenter.BaseBeanPresenter;
import com.pem.ui.presentation.common.reactor.VaadinReactor;
import com.pem.ui.presentation.common.view.BaseBeanForm;
import com.pem.ui.presentation.common.view.provider.BeanFormView;
import com.pem.ui.presentation.process.ProcessMainView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

@BeanFormView(ExecutionProcessObject.class)
public class ProcessFormView extends BaseBeanForm<ExecutionProcessObject> {
    private ProcessFormPresenter presenter;
    private NavigationManager navigator;

    private TextField operationField = new TextField("Operation");

    private Button runProcess = new Button("Run");
    @Override
    protected BaseBeanPresenter getPresenter() {
        return presenter;
    }

    @Override
    public Mono<Void> bind(ExecutionProcessObject bean) {
        return super.bind(bean)
                .doOnSuccess(signal -> addRunProcessPublisher(bean));
    }

    @Override
    protected Layout createFormComponent() {
        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(getNameField());
        formLayout.addComponent(operationField);

        formLayout.addComponent(getDescriptionField());
        formLayout.addComponent(getCreatedWhenField());
        formLayout.addComponent(getModifyWhenField());

        return formLayout;
    }

    @Override
    protected Layout createTopToolbar() {
        return new HorizontalLayout(runProcess);
    }

    private void addRunProcessPublisher(ExecutionProcessObject bean) {
        VaadinReactor.buttonClickPublisher(runProcess)
                .map(clickEvent -> NavigationParams.builder())
                .doOnNext(builder -> builder.setViewName(ProcessMainView.VIEW_NAME))
                .doOnNext(builder -> builder.addUrlParam(NavigationConst.RUN_PROCESS_PARAM, String.valueOf(bean.getId())))
                .subscribe(builder -> navigator.navigate(builder.build()));
    }
    @Autowired
    public void setPresenter(ProcessFormPresenter presenter) {
        this.presenter = presenter;
    }

    @Autowired
    public void setNavigator(NavigationManager navigator) {
        this.navigator = navigator;
    }
}
