package com.pem.ui.presentation.common.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

public abstract class AbstractBeanForm<B> extends BindForm<B> {

    private Layout bottomToolbar;

    private Button submitButton = new Button("Submit");

    private Button cancelButton = new Button("Cancel");

    @PostConstruct
    void init() {
        VerticalLayout mainLayout = new VerticalLayout();

        mainLayout.addComponent(createTopToolbar());
        mainLayout.addComponent(createFormComponent());

        bottomToolbar = createBottomToolbar();
        mainLayout.addComponent(bottomToolbar);
        setCompositionRoot(mainLayout);

        addFormModifiedListener(new FormModifiedListener() {
            @Override
            public void formModified(FormModifiedEvent event) {
                bottomToolbar.setVisible(event.getForm().isModified());
            }
        });
    }

    protected Button getSubmitButton() {
        return submitButton;
    }

    protected Button getCancelButton() {
        return cancelButton;
    }

    protected abstract Layout createTopToolbar();

    protected abstract Layout createFormComponent();

    protected Layout createBottomToolbar() {
        HorizontalLayout bottomToolbar = new HorizontalLayout();
        bottomToolbar.setVisible(false);

        bottomToolbar.addComponent(submitButton);
        bottomToolbar.addComponent(cancelButton);
        initBottomButtons();
        return bottomToolbar;
    }

    private void initBottomButtons() {
        submitButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (isValid()) {
                    commit();
                }
            }
        });

        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                discard();
            }
        });
    }
}
