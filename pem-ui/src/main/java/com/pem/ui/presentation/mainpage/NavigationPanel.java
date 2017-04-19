package com.pem.ui.presentation.mainpage;

import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.common.navigator.NavigationManager;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringComponent
public class NavigationPanel extends HorizontalLayout {

    @Autowired
    private NavigationManager navigator;

    public void addNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addClickListener((Button.ClickListener) event -> doNavigate(viewName));

        addComponent(button);
    }

    private void doNavigate(String viewName) {
        navigator.navigate(NavigationParams.builder().setViewName(viewName).build());
    }

    @PostConstruct
    void init() {
        setHeight("50px");
        setWidth("100%");
        setSpacing(true);
        setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
    }

}
