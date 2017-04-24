package com.pem.ui.presentation.process.form;

import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.ui.presentation.common.presenter.BaseBeanPresenter;
import com.pem.ui.presentation.common.view.BaseBeanForm;
import com.pem.ui.presentation.common.view.provider.BeanFormView;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import org.springframework.beans.factory.annotation.Autowired;

@BeanFormView(ExecutionProcessObject.class)
public class ProcessFormView extends BaseBeanForm<ExecutionProcessObject> {
    private ProcessFormPresenter presenter;

    @Override
    protected BaseBeanPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected Layout createFormComponent() {
        FormLayout formLayout = new FormLayout();
        formLayout.addComponent(getNameField());

        formLayout.addComponent(getDescriptionField());
        formLayout.addComponent(getCreatedWhenField());
        formLayout.addComponent(getModifyWhenField());

        return formLayout;
    }

    @Override
    protected Layout createTopToolbar() {
        return new HorizontalLayout();
    }

    @Autowired
    public void setPresenter(ProcessFormPresenter presenter) {
        this.presenter = presenter;
    }
}
