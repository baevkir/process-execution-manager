package com.pem.ui.presentation.operation.table.view;

import com.google.common.base.Preconditions;
import com.pem.core.common.annotation.spring.PrototypeComponent;
import com.pem.model.operation.common.OperationObject;
import com.pem.ui.common.reactor.VaadinReactor;
import com.pem.ui.integration.viewprovider.PemViewProvider;
import com.pem.ui.presentation.operation.popup.common.view.PopupView;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@PrototypeComponent
public class OperationPopupImpl extends Window implements OperationsPopup {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationPopupImpl.class);

    private PemViewProvider viewProvider;
    private Button submitButton = new Button("Submit");
    private Button cancelButton = new Button("Cancel");
    private final Panel contentPanel = new Panel();

    @Override
    public <O extends OperationObject> Mono<O> openOperation(O operation) {
        Assert.notNull(operation, "Operation is null.");
        PopupView<O, ?> view = getView(operation);

        Binder<O> binder = view.bind(operation);
        Assert.isInstanceOf(Component.class, view, String.format("Popup view %s is not a Component.", view));
        contentPanel.setContent((Component) view);
        setVisible(true);

        return VaadinReactor.buttonClickPublisher(submitButton).next()
                .flatMap(clickEvent -> saveOperation(binder)).next()
                .doOnSuccess(operationObject -> closeWindow());
    }

    private <O extends OperationObject> Mono<O> saveOperation(Binder<O> binder) {
        return Mono.just(binder.validate()).map(validationStatus -> {
            if (validationStatus.hasErrors()) {
                String message = getValidationMessage(validationStatus);
                Notification.show("Validation error:", message, Notification.Type.ERROR_MESSAGE);
                throw new RuntimeException("Validation Failed: " + message);
            }
            O operation = validationStatus.getBinder().getBean();
            validationStatus.getBinder().writeBeanIfValid(operation);
            return validationStatus.getBinder().getBean();
        }).retry();

    }

    private String getValidationMessage(BinderValidationStatus<?> validationStatus) {
        StringBuilder validationMessage = new StringBuilder().append("<br>");
        validationStatus.getValidationErrors().forEach(validationResult ->
                validationMessage.append("-").append(validationResult.getErrorMessage()).append("<br>"));

        return validationMessage.toString();
    }

    @SuppressWarnings("unchecked")
    private <O extends OperationObject> PopupView<O, ?> getView(O operation) {
        View view = viewProvider.getView(operation.getClass());
        Preconditions.checkNotNull(view, "Cannot fin view for %s. View is null", operation.getClass());
        Preconditions.checkState(view instanceof PopupView, "Cannot fin view for %s. View is not Popup View", operation.getClass());

        return (PopupView<O, ?>) view;
    }

    private void closeWindow() {
        contentPanel.setContent(null);
        setVisible(false);
        close();
    }

    private Flux<Button.ClickEvent> getOperationPublisher() {
        return Flux.create(emitter -> {
            submitButton.addClickListener(event -> {
                    emitter.next(event):
        }));
    }

    @PostConstruct
    void init() {
        UI.getCurrent().addWindow(this);
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(contentPanel);

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addComponents(submitButton, cancelButton);
        layout.addComponent(toolbar);

        setContent(layout);

        VaadinReactor.buttonClickPublisher(cancelButton).next()
                .doOnSuccess(event -> closeWindow())
                .subscribe();
    }

    @PreDestroy
    void destroy() {
        UI.getCurrent().removeWindow(this);
    }

    @Autowired
    public void setViewProvider(PemViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }
}
