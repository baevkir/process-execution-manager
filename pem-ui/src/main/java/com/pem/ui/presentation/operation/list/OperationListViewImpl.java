package com.pem.ui.presentation.operation.list;

import com.google.common.eventbus.EventBus;
import com.pem.model.operation.common.OperationDTO;
import com.pem.ui.presentation.common.view.BeanFormPanel;
import com.pem.ui.presentation.operation.event.OpenOperationEvent;
import com.pem.ui.presentation.operation.event.ShowOperationsListEvent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;

@UIScope
@SpringView(name = OperationListView.VIEW_NAME)
public class OperationListViewImpl extends HorizontalLayout implements OperationListView {

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

        String parameters = event.getParameters();
        if (StringUtils.isEmpty(parameters) || !StringUtils.isNumeric(parameters)) {
            contentPanel.setContent(null);
            return;
        }
        BigInteger operationId = new BigInteger(parameters);
        eventBus.post(new OpenOperationEvent(operationId));
    }

    @Override
    public void loadOperations(List<OperationDTO> operations) {
        operationList.load(operations);
    }

    @Override
    public void openOperation(BeanFormPanel operationView) {
        contentPanel.setContent(operationView);
    }


    @PostConstruct
    void init() {
        operationListPresenter.bind(this);
        setSizeFull();

        addComponent(operationList);
        addComponent(contentPanel);
        setExpandRatio(contentPanel, 1.0f);
    }
}
