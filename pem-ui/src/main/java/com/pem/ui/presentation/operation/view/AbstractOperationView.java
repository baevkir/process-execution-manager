package com.pem.ui.presentation.operation.view;

import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.view.BaseBeanForm;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

public abstract class AbstractOperationView<O extends OperationDTO> extends BaseBeanForm<O> implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    @Override
    public void bind(O bean) {
        super.bind(bean);
        addSaveHandler();
    }

    @Override
    protected Layout createTopToolbar() {
        return new HorizontalLayout();
    }

    private void addSaveHandler() {
        getBinder().addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {

            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                           }
        });
    }

}
