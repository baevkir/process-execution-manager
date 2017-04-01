package com.pem.ui.presentation.common.view;

import com.pem.ui.presentation.common.presenter.BaseBeanPresenter;
import com.pem.ui.presentation.common.reactor.VaadinReactor;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

public abstract class AbstractBeanForm<B> extends CustomComponent implements BeanView<B> {

    private Layout bottomToolbar;
    private Layout topToolbar;
    private final Panel contentPanel = new Panel();

    private Flux<Button.ClickEvent> submitPublisher;
    private Flux<Button.ClickEvent> cancelPublisher;

    protected abstract <V extends BeanView<B>> BaseBeanPresenter<B, V> getPresenter();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    @Override
    public Mono<Void> bind(B bean) {
        return getPresenter().bindBean(bean)
                .doOnNext(binder -> submitPublisher.filter(event -> binder.isModified()).subscribe(clickEvent -> submit(binder)))
                .doOnNext(binder -> cancelPublisher.filter(event -> binder.isModified()).subscribe(clickEvent -> cancel(binder)))
                .doOnSuccess(binder -> VaadinReactor.binderEditPublisher(binder).subscribe(event -> bottomToolbar.setVisible(true)))
                .doOnSuccess(binder -> initFormElements())
                .then();
    }

    protected abstract void initFormElements();

    protected abstract Layout createFormComponent();

    protected abstract Layout createTopToolbar();

    protected Layout createBottomToolbar() {
        HorizontalLayout bottomToolbar = new HorizontalLayout();
        bottomToolbar.setVisible(false);

        Button submitButton = new Button("Submit");
        submitPublisher = VaadinReactor.buttonClickPublisher(submitButton);

        Button cancelButton = new Button("Cancel");
        cancelPublisher = VaadinReactor.buttonClickPublisher(cancelButton);

        bottomToolbar.addComponent(submitButton);
        bottomToolbar.addComponent(cancelButton);

        return bottomToolbar;
    }

    @PostConstruct
    void init() {
        getPresenter().bind(this);
        VerticalLayout mainLayout = new VerticalLayout();

        topToolbar = createTopToolbar();
        mainLayout.addComponent(topToolbar);
        mainLayout.addComponent(contentPanel);

        bottomToolbar = createBottomToolbar();
        mainLayout.addComponent(bottomToolbar);
        setCompositionRoot(mainLayout);
        contentPanel.setContent(createFormComponent());
    }

    private void submit(BeanFieldGroup<B> binder) {
        try {
            binder.commit();
            bottomToolbar.setVisible(false);
        } catch (FieldGroup.CommitException exception) {
            Notification.show(exception.getLocalizedMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    private void cancel(BeanFieldGroup<B> binder) {
        binder.discard();
        bottomToolbar.setVisible(false);
    }

}
