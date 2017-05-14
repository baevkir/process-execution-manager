package com.pem.ui.presentation.process.form;

import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.ui.presentation.common.presenter.BaseBeanPresenter;
import com.pem.ui.presentation.common.reactor.VaadinReactor;
import com.pem.ui.presentation.common.view.BaseBeanForm;
import com.pem.ui.presentation.common.view.provider.BeanFormView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

@BeanFormView(ExecutionProcessObject.class)
public class ProcessFormView extends BaseBeanForm<ExecutionProcessObject> {
    private ProcessFormPresenter presenter;

    private TextField operationField = new TextField("Operation");

    private Button runProcess = new Button("Run");

    public Flux<Button.ClickEvent> getRunProcessPublisher() {
        return VaadinReactor.buttonClickPublisher(runProcess);
    }

    @Override
    protected BaseBeanPresenter getPresenter() {
        return presenter;
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

    @Autowired
    public void setPresenter(ProcessFormPresenter presenter) {
        this.presenter = presenter;
    }
}
