package com.pem.ui.presentation.process;

import com.vaadin.ui.HorizontalLayout;

//@UIScope
//@SpringComponent
public class ProcessesList extends HorizontalLayout {

    private static final String COLUMN_NAME = "name";

//    private final Table processesTable = new Table();
//    private final BeanItemContainer<ExecutionProcessObject> processesContainer = new BeanItemContainer<>(ExecutionProcessObject.class);
//
//    private NavigationManager navigator;
//
//    public void load(Flux<ExecutionProcessObject> process) {
//        process.doOnSubscribe(subscription -> processesContainer.removeAllItems())
//                .subscribe(operation -> processesContainer.addBean(operation));
//    }
//
//    @PostConstruct
//    void init() {
//        VerticalLayout mainLayout = new VerticalLayout();
//        addComponent(mainLayout);
//
//        mainLayout.addComponent(processesTable);
//        mainLayout.setExpandRatio(processesTable, 1.0f);
//
//        processesTable.setSelectable(true);
//        processesTable.setHeight("100%");
//        processesTable.setWidth("250px");
//
//        processesTable.setContainerDataSource(processesContainer);
//        processesTable.setVisibleColumns(COLUMN_NAME);
//
//        processesTable.setColumnHeader(COLUMN_NAME, "Name");
//
//        VaadinReactor.tableClickPublisher(processesTable)
//                .map(itemClickEvent -> itemClickEvent.getItemId())
//                .filter(itemId -> itemId != null)
//                .cast(ExecutionProcessObject.class)
//                .map(processObject -> processObject.getId())
//                .subscribe(processId -> navigateToProcess(processId.toString()));
//
//    }
//
//    private void navigateToProcess(String processId) {
//        Assert.notNull(processId, "Cannot navigate. Process ID is NULL");
//        NavigationParams params = NavigationParams.builder()
//                .setViewName(ProcessMainView.VIEW_NAME)
//                .addUrlParam(NavigationConst.ID_PARAM, processId)
//                .build();
//        navigator.navigate(params);
//    }
//
//    @Autowired
//    public void setNavigator(NavigationManager navigator) {
//        this.navigator = navigator;
//    }

}
