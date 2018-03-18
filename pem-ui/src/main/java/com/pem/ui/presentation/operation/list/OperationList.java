package com.pem.ui.presentation.operation.list;

import com.vaadin.ui.HorizontalLayout;

//@UIScope
//@SpringComponent
public class OperationList extends HorizontalLayout {

//    private static final String COLUMN_NAME = "name";
//    private static final String COLUMN_STATUS = "active";
//
//    @Autowired
//    private NavigationManager navigator;
//
//    private Button newOperationButton;
//    private Button refreshButton;
//
//    private final Table operationTable = new Table();
//    private final BeanItemContainer<OperationObject> operationContainer = new BeanItemContainer<>(OperationObject.class);
//
//    public void load(Flux<OperationObject> operationPublisher) {
//        operationPublisher.doOnSubscribe(subscription -> operationContainer.removeAllItems())
//                .sort((first, second) -> Boolean.compare(second.isActive(), first.isActive()))
//                .subscribe(operation -> operationContainer.addBean(operation));
//    }
//
//    public Flux<Button.ClickEvent> getRefreshPublisher() {
//        return VaadinReactor.buttonClickPublisher(refreshButton);
//    }
//
//    @PostConstruct
//    void init() {
//        VerticalLayout mainLayout = new VerticalLayout();
//        addComponent(mainLayout);
//
//        mainLayout.addComponent(createTopToolbar());
//        mainLayout.addComponent(operationTable);
//        mainLayout.setExpandRatio(operationTable, 1.0f);
//
//        operationTable.setSelectable(true);
//        operationTable.setHeight("100%");
//        operationTable.setHeight("250px");
//
//        operationTable.setContainerDataSource(operationContainer);
//        operationTable.setVisibleColumns(COLUMN_NAME, COLUMN_STATUS);
//
//        operationTable.setColumnHeader(COLUMN_NAME, "Name");
//        operationTable.setColumnHeader(COLUMN_STATUS, "Active");
//
//        VaadinReactor.tableClickPublisher(operationTable)
//                .map(itemClickEvent -> itemClickEvent.getItemId())
//                .filter(itemId -> itemId != null)
//                .cast(OperationObject.class)
//                .map(operation -> operation.getId())
//                .subscribe(operationId -> navigateToOperation(operationId.toString()));
//
//        VaadinReactor.buttonClickPublisher(newOperationButton)
//                .subscribe(clickEvent -> navigateToOperation(NEW_OBJECT_VALUE));
//    }
//
//    private Layout createTopToolbar() {
//        HorizontalLayout toolbar = new HorizontalLayout();
//        toolbar.addComponent(createNewOperationButton());
//        toolbar.addComponent(createRefreshButton());
//        return toolbar;
//    }
//
//    private Button createNewOperationButton() {
//        newOperationButton = new Button("New operation");
//        return newOperationButton;
//    }
//
//    private Button createRefreshButton() {
//        refreshButton = new Button("Refresh");
//        return refreshButton;
//    }
//
//    private void navigateToOperation(String operationId) {
//        Assert.notNull(operationId, "Cannot navigate. Operation is NULL");
//        NavigationParams params = NavigationParams.builder()
//                .setViewName(OperationListView.VIEW_NAME)
//                .addUrlParam(NavigationConst.ID_PARAM, operationId)
//                .build();
//        navigator.navigate(params);
//    }
}
