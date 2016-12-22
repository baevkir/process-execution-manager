package com.pem.ui.presentation.common.page;

import com.pem.ui.presentation.operation.list.OperationListView;
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

    private final NavigationPanel navigationPanel = new NavigationPanel();
    private final Panel contentPanel = new Panel();

    protected void init(VaadinRequest vaadinRequest) {
        UI.setCurrent(this);

        Navigator navigator = new Navigator(this, contentPanel);
        navigator.addProvider(viewProvider);

        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setContent(root);

//        SliderPanel sliderPanel = new SliderPanelBuilder(navigationPanel)
//                .mode(SliderMode.TOP)
//                .tabPosition(SliderTabPosition.MIDDLE)
//                .build();

        root.addComponent(navigationPanel);
        navigationPanel.addNavigationButton("Operations", OperationListView.VIEW_NAME);
        navigationPanel.addNavigationButton("Processes", ProcessMainView.VIEW_NAME);

        root.addComponent(contentPanel);

        if (navigator.getState().isEmpty()) {
            navigator.navigateTo(OperationListView.VIEW_NAME);
        } else {
            navigator.navigateTo(navigator.getState());
        }

    }
}
