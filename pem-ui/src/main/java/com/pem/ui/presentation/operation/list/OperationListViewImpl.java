package com.pem.ui.presentation.operation.list;

import com.google.common.eventbus.EventBus;
import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.operation.event.ShowOperationsListEvent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@UIScope
@SpringView(name = OperationListViewImpl.VIEW_NAME)
public class OperationListViewImpl extends HorizontalLayout implements OperationListView {
    public static final String VIEW_NAME = "operations";

    @Autowired
    private EventBus eventBus;

    @Autowired
    private OperationList operationList;

    @Autowired
    private OperationListPresenter operationListPresenter;

    private final Panel contentPanel = new Panel();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (!operationList.isDataLoaded()) {
            eventBus.post(new ShowOperationsListEvent());
        }
    }

    @Override
    public void loadOperations(List<OperationDTO> operations) {
        operationList.load(operations);
    }

    @PostConstruct
    public void init() {
        operationListPresenter.bind(this);
        setSizeFull();
        setMargin(true);

        addComponent(operationList);
        addComponent(contentPanel);
        setExpandRatio(contentPanel, 1.0f);
    }
}
