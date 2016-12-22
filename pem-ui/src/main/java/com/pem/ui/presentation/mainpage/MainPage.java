package com.pem.ui.presentation.mainpage;

import com.pem.ui.presentation.operation.list.OperationListViewImpl;
import com.pem.ui.presentation.process.ProcessMainView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("pem")
@Title("Process Execution Manager")
@SpringUI
public class MainPage extends UI {

    @Autowired
    private SpringViewProvider viewProvider;

    @Autowired
    private NavigationPanel navigationPanel;

    private final Panel contentPanel = new Panel();

    protected void init(VaadinRequest vaadinRequest) {
        UI.setCurrent(this);

        Navigator navigator = new Navigator(this, contentPanel);
        navigator.addProvider(viewProvider);
        setNavigator(navigator);

        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setContent(root);

        root.addComponent(navigationPanel);
        navigationPanel.addNavigationButton("Operations", OperationListViewImpl.VIEW_NAME);
        navigationPanel.addNavigationButton("Processes", ProcessMainView.VIEW_NAME);

        contentPanel.setSizeFull();
        root.addComponent(contentPanel);

        root.setExpandRatio(contentPanel, 1.0f);

        if (navigator.getState().isEmpty()) {
            navigator.navigateTo(OperationListViewImpl.VIEW_NAME);
        } else {
            navigator.navigateTo(navigator.getState());
        }

    }
}
