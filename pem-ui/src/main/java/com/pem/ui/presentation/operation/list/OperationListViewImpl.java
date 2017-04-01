package com.pem.ui.presentation.operation.list;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.pem.ui.presentation.operation.view.BaseOperationView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.EventListener;

@UIScope
@SpringView(name = OperationListView.VIEW_NAME)
public class OperationListViewImpl extends HorizontalLayout implements OperationListView {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationListViewImpl.class);
    @Autowired
    private OperationList operationList;

    @Autowired
    private OperationListPresenter presenter;

    private final Panel contentPanel = new Panel();

    private EventBus eventBus = new EventBus();

    private Flux<ViewChangeListener.ViewChangeEvent> viewChangePublisher;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        eventBus.post(event);
    }

    @Override
    public void openOperation(BaseOperationView operationView) {
        contentPanel.setContent(operationView);
    }

    @Override
    public OperationList getOperationList() {
        return operationList;
    }

    @Override
    public Flux<ViewChangeListener.ViewChangeEvent> getViewChangePublisher() {
        return viewChangePublisher
                .doOnNext(event -> LOGGER.debug("Start to handle event {}.", event));
    }

    @PostConstruct
    void init() {
        setSizeFull();

        addComponent(operationList);
        addComponent(contentPanel);
        setExpandRatio(contentPanel, 1.0f);
        viewChangePublisher = Flux.create(emitter -> initConsumer(emitter));
        presenter.bind(this);
    }

    private void initConsumer(FluxSink<ViewChangeListener.ViewChangeEvent> emitter) {
        eventBus.register(new EventListener() {
            @Subscribe
            public void subscribeEvent(ViewChangeListener.ViewChangeEvent event) {
                emitter.next(event);
            }
        });
    }
}
