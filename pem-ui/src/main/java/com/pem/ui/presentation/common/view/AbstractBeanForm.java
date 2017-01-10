package com.pem.ui.presentation.common.view;

import com.pem.ui.presentation.common.presenter.BaseBeanPresenter;
import com.pem.ui.presentation.common.rx.RxVaadin;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import javax.annotation.PostConstruct;

public abstract class AbstractBeanForm<B> extends CustomComponent implements BeanView<B> {

    private Layout bottomToolbar;
    private Layout topToolbar;
    private final Panel contentPanel = new Panel();

    private Observable<Button.ClickEvent> submitObservable;
    private Observable<Button.ClickEvent> cancelObservable;
    private Subject<B> beanSubject = PublishSubject.create();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    @Override
    public void bind(B bean) {
        beanSubject.onNext(bean);
    }

    @Override
    public void redrawForm() {
        contentPanel.setContent(createFormComponent());
    }

    @Override
    public Subject<B> getBeanSubject() {
        return beanSubject;
    }

    public Observable<Button.ClickEvent> getSubmitObservable() {
        return submitObservable;
    }

    @Override
    public Observable<Button.ClickEvent> getCancelObservable() {
        return cancelObservable;
    }

    @Override
    public Layout getTopToolbar() {
        return topToolbar;
    }

    @Override
    public Layout getBottomToolbar() {
        return bottomToolbar;
    }

    protected abstract <V extends BeanView<B>> BaseBeanPresenter<B, V> getPresenter();

    protected abstract Layout createTopToolbar();

    protected abstract Layout createFormComponent();

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
    }

    protected Layout createBottomToolbar() {
        HorizontalLayout bottomToolbar = new HorizontalLayout();
        bottomToolbar.setVisible(false);

        Button submitButton = new Button("Submit");
        submitObservable = RxVaadin.buttonClickObservable(submitButton);

        Button cancelButton = new Button("Cancel");
        cancelObservable = RxVaadin.buttonClickObservable(cancelButton);

        bottomToolbar.addComponent(submitButton);
        bottomToolbar.addComponent(cancelButton);

        return bottomToolbar;
    }

}
