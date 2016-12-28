package com.pem.ui.presentation.process;

import com.pem.logic.service.process.ExecutionProcessService;
import com.pem.ui.presentation.common.presenter.AbstractPresenter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class ProcessMainPresenter extends AbstractPresenter<ProcessMainView> {

    @Autowired
    private ExecutionProcessService processService;
}
