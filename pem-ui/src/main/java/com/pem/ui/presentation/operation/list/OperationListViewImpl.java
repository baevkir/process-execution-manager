package com.pem.ui.presentation.operation.list;

import com.pem.ui.presentation.operation.view.BaseOperationView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = OperationListView.VIEW_NAME)
public class OperationListViewImpl extends HorizontalLayout implements OperationListView {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationListViewImpl.class);

    private OperationList operationList;

    private OperationListPresenter presenter;

    private final Panel contentPanel = new Panel();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    @Override
    public void openOperation(BaseOperationView operationView) {
        contentPanel.setContent(operationView);
    }

    @PostConstruct
    void init() {
        setSizeFull();

        addComponent(operationList);
        addComponent(contentPanel);
        setExpandRatio(contentPanel, 1.0f);
        presenter.bind(this);
    }

    @Autowired
    public void setOperationList(OperationList operationList) {
        this.operationList = operationList;
    }

    @Autowired
    public void setPresenter(OperationListPresenter presenter) {
        this.presenter = presenter;
    }
}
