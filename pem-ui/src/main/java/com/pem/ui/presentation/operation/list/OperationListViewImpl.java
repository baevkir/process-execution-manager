package com.pem.ui.presentation.operation.list;

import com.pem.ui.presentation.common.view.BindForm;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import org.springframework.beans.factory.annotation.Autowired;
import rx.subjects.PublishSubject;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = OperationListView.VIEW_NAME)
public class OperationListViewImpl extends HorizontalLayout implements OperationListView {

    @Autowired
    private OperationList operationList;

    @Autowired
    private OperationListPresenter operationListPresenter;

    private final Panel contentPanel = new Panel();

    private PublishSubject<ViewChangeListener.ViewChangeEvent>  viewSubject;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        viewSubject.onNext(event);
    }

    @Override
    public PublishSubject<ViewChangeListener.ViewChangeEvent> getViewSubject() {
        return viewSubject;
    }

    @Override
    public void openOperation(BindForm operationView) {
        contentPanel.setContent(operationView);
    }

    public OperationList getOperationList() {
        return operationList;
    }

    @PostConstruct
    void init() {
        viewSubject = PublishSubject.create();
        setSizeFull();

        addComponent(operationList);
        addComponent(contentPanel);
        setExpandRatio(contentPanel, 1.0f);
        operationListPresenter.bind(this);
    }
}
