package com.pem.ui.presentation.mainpage;

import com.pem.ui.common.StyleConstants;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

import javax.annotation.PostConstruct;

@UIScope
@SpringComponent
public class NavigationPanel extends HorizontalLayout {

    public void addNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(StyleConstants.NAVIGATION_PANEL_BUTTON_STYLE);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                doNavigate(viewName);
            }
        });

        addComponent(button);
        setComponentAlignment(button, Alignment.MIDDLE_LEFT);
    }

    private void doNavigate(String viewName) {
        getUI().getNavigator().navigateTo(viewName);
    }

    @PostConstruct
    public void init() {
        setHeight("50px");
        setWidth("100%");
        setMargin(true);
        addStyleName(StyleConstants.NAVIGATION_PANEL_STYLE);
    }

}
