package com.pem.ui.presentation.operation.view;

import com.pem.model.operation.common.OperationObject;
import com.pem.ui.presentation.common.navigator.NavigationConst;
import com.pem.ui.presentation.common.navigator.NavigationManager;
import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.common.reactor.VaadinReactor;
import com.pem.ui.presentation.common.view.BaseBeanForm;
import com.pem.ui.presentation.operation.list.OperationListView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public abstract class BaseOperationView<O extends OperationObject> extends BaseBeanForm<O> {

    private Button createProcess = new Button("New Process");

    @Autowired
    private NavigationManager navigator;

    @Override
    protected Layout createTopToolbar() {
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addComponent(createProcess);
        return toolbar;
    }

    @Override
    public Mono<Void> bind(O bean) {
        return super.bind(bean)
                .doOnSuccess(aVoid -> createProcess.setVisible(isCreateProcessAvailable(bean)))
                .doOnSuccess(aVoid -> VaadinReactor.buttonClickPublisher(createProcess)
                        .filter(clickEvent -> isCreateProcessAvailable(bean))
                        .map(clickEvent -> NavigationParams.builder())
                        .doOnNext(builder -> builder.setViewName(OperationListView.VIEW_NAME))
                        .doOnNext(builder -> builder.addUrlParam(NavigationConst.CREATE_PROCESS_PARAM, String.valueOf(bean.getId())))
                        .subscribe(builder -> navigator.navigate(builder.build())));
    }

    private boolean isCreateProcessAvailable(O bean) {
        if (bean.getId() == null) {
            return false;
        }
        return bean.isActive();
    }
}
