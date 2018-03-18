package com.pem.ui.presentation.operation.table.view;

import com.pem.model.operation.common.OperationObject;
import com.pem.ui.common.PublisherDataProvider;
import com.pem.ui.presentation.operation.table.presenter.OperationTablePresenterImpl;
import com.pem.ui.presentation.operation.table.presenter.OperationsTablePresenter;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = OperationsTableView.VIEW_NAME)
public class OperationTableViewImpl extends HorizontalLayout implements OperationsTableView {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationTablePresenterImpl.class);

    private OperationsTable operationsTable;
    private OperationsTablePresenter presenter;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOGGER.info("Load view {} event: {}.", getClass(), event);
    }

    @Override
    public void setOperationsProvider(PublisherDataProvider<OperationObject> dataProvider) {
        operationsTable.setDataProvider(dataProvider);
    }

    @PostConstruct
    void init() {
        presenter.setView(this);
        addComponentsAndExpand(operationsTable);
    }

    @Autowired
    public void setPresenter(OperationsTablePresenter presenter) {
        this.presenter = presenter;
    }

    @Autowired
    public void setOperationsTable(OperationsTable operationsTable) {
        this.operationsTable = operationsTable;
    }
}
