package com.pem.ui.presentation.common.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import io.reactivex.Observable;
import io.reactivex.subjects.Subject;


public interface BeanView<B> extends View {
    void bind(B bean);
    void redrawForm();
    Subject<B> getBeanSubject();
    Observable<Button.ClickEvent> getSubmitObservable();
    Observable<Button.ClickEvent> getCancelObservable();
    Layout getTopToolbar();
    Layout getBottomToolbar();
}
