package com.pem.ui.presentation.operation.view;

import com.google.common.eventbus.EventBus;
import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.view.BaseBeanForm;
import com.pem.ui.presentation.operation.event.SaveOperationEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractOperationView<O extends OperationDTO> extends BaseBeanForm<O> implements View {

    @Autowired
    private EventBus eventBus;

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
                eventBus.post(new SaveOperationEvent(getBean()));
            }
        });
    }

}
