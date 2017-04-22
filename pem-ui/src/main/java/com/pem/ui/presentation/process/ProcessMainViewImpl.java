package com.pem.ui.presentation.process;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = ProcessMainView.VIEW_NAME)
public class ProcessMainViewImpl extends HorizontalLayout implements ProcessMainView {

    private ProcessMainPresenter presenter;
    private ProcessesList processesList;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    @Override
    public ProcessesList getProcessesList() {
        return processesList;
    }


    @PostConstruct
    void init() {
        setSizeFull();

        addComponent(processesList);
        presenter.bind(this);
    }

    @Autowired
    public void setPresenter(ProcessMainPresenter presenter) {
        this.presenter = presenter;
    }

    @Autowired
    public void setProcessesList(ProcessesList processesList) {
        this.processesList = processesList;
    }
}
