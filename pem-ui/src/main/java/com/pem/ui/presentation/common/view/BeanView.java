package com.pem.ui.presentation.common.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import rx.Observable;
import rx.subjects.PublishSubject;

public interface BeanView<B> extends View {
    void bind(B bean);
    void redrawForm();
    PublishSubject<B> getBeanSubject();
    Observable<Button.ClickEvent> getSubmitObservable();
    Observable<Button.ClickEvent> getCancelObservable();
    Layout getTopToolbar();
    Layout getBottomToolbar();
}
