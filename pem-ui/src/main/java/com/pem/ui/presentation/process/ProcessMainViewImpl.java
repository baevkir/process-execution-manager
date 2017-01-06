package com.pem.ui.presentation.process;

import com.pem.model.proccess.ExecutionProcessDTO;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = ProcessMainView.VIEW_NAME)
public class ProcessMainViewImpl extends HorizontalLayout implements ProcessMainView {

    @Autowired
    private ProcessMainPresenter presenter;

    @Autowired
    private ProcessesList processesList;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        if (!processesList.isDataLoaded()) {
//            eventBus.post(new LoadProcessesEvent());
//        }
    }

    @Override
    public void load(List<ExecutionProcessDTO> process) {
        processesList.load(process);
    }


    @PostConstruct
    void init() {
        presenter.bind(this);
        setSizeFull();

        addComponent(processesList);
    }
}
