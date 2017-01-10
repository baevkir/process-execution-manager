package com.pem.ui.presentation.operation.list;

import com.pem.ui.presentation.operation.view.BaseOperationView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = OperationListView.VIEW_NAME)
public class OperationListViewImpl extends HorizontalLayout implements OperationListView {

    @Autowired
    private OperationList operationList;

    @Autowired
    private OperationListPresenter operationListPresenter;

    private final Panel contentPanel = new Panel();

    private Subject<ViewChangeListener.ViewChangeEvent> viewSubject;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
         viewSubject.onNext(event);
    }

    @Override
    public Observable<ViewChangeListener.ViewChangeEvent> getViewObservable() {
        return viewSubject;
    }

    @Override
    public void openOperation(BaseOperationView operationView) {
        contentPanel.setContent(operationView);
    }

    public OperationList getOperationList() {
        return operationList;
    }

    @PostConstruct
    void init() {
        viewSubject = PublishSubject.<ViewChangeListener.ViewChangeEvent>create().toSerialized();
        setSizeFull();

        addComponent(operationList);
        addComponent(contentPanel);
        setExpandRatio(contentPanel, 1.0f);
        operationListPresenter.bind(this);
    }
}
