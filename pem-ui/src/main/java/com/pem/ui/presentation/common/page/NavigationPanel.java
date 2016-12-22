package com.pem.ui.presentation.common.page;

import com.pem.ui.common.StyleConstants;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class NavigationPanel extends HorizontalLayout {

    public NavigationPanel() {
        addStyleName(StyleConstants.NAVIGATION_PANEL_STYLE);
    }

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
    }

    private void doNavigate(String viewName) {
        getUI().getNavigator().navigateTo(viewName);
    }
}
