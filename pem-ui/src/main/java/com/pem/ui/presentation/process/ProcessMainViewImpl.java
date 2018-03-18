package com.pem.ui.presentation.process;

import com.pem.ui.presentation.process.form.ProcessFormView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;

import javax.annotation.PostConstruct;

@SpringView(name = ProcessMainView.VIEW_NAME)
public class ProcessMainViewImpl extends HorizontalLayout implements ProcessMainView {

    private ProcessMainPresenter presenter;
    private ProcessesList processesList;
    private final Panel contentPanel = new Panel();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    @Override
    public void openProcess(ProcessFormView operationView) {
//        contentPanel.setContent(operationView);
    }

    @PostConstruct
    void init() {
        setSizeFull();
        addComponent(processesList);
        addComponent(contentPanel);
        setExpandRatio(contentPanel, 1.0f);
//        presenter.bind(this);
    }

//    @Autowired
    public void setPresenter(ProcessMainPresenter presenter) {
        this.presenter = presenter;
    }

//    @Autowired
    public void setProcessesList(ProcessesList processesList) {
        this.processesList = processesList;
    }
}
