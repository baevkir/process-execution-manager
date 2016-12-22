package com.pem.ui.presentation.process;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;

@SpringView(name = ProcessMainView.VIEW_NAME)
public class ProcessMainView extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "process-view";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
