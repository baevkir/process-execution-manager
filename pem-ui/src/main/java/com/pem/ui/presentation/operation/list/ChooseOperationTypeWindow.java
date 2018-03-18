package com.pem.ui.presentation.operation.list;

import com.vaadin.ui.Window;

//@UIScope
//@SpringComponent
public class ChooseOperationTypeWindow extends Window {

//    @Autowired
//    private PemViewProvider viewProvider;
//
//    private Button okButton;
//
//    private ListSelect operationTypes = new ListSelect();
//
//    public Mono<OperationViewObject> getPublisher() {
//        return VaadinReactor.buttonClickPublisher(okButton).next()
//                .then(clickEvent -> getValue())
//                .doOnSuccess(value -> close());
//    }
//
//    private Mono<OperationViewObject> getValue() {
//        return Mono.justOrEmpty(operationTypes.getValue())
//                .cast(OperationViewObject.class);
//    }
//
//    @PostConstruct
//    void init() {
//        setCaption("Choose type of operation for create.");
//        setWidth("350px");
//        setHeight("450px");
//        center();
//        setResizable(false);
//        FormLayout mainLayout = new FormLayout();
//        setContent(mainLayout);
//
//        BeanItemContainer<OperationViewObject> operationViewsContainer = new BeanItemContainer<>(viewProvider.getOperationViews());
//        operationTypes.setItemCaptionPropertyId("name");
//        operationTypes.setMultiSelect(false);
//        operationTypes.setNullSelectionAllowed(false);
//        operationTypes.setContainerDataSource(operationViewsContainer);
//        operationTypes.setSizeFull();
//
//        mainLayout.addComponent(operationTypes);
//
//        mainLayout.addComponent(createOKButton());
//    }
//
//    private Button createOKButton() {
//        okButton = new Button("OK");
//        return okButton;
//    }
}
