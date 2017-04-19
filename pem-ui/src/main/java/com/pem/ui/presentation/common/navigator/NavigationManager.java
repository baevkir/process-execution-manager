package com.pem.ui.presentation.common.navigator;

import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.UI;
import reactor.core.publisher.Flux;

public interface NavigationManager {
    NavigationParams getNavigationParams();
    void navigate(NavigationParams params);
    Flux<NavigationParams> onNavigationChange();
    void register(UI ui, ViewDisplay viewDisplay);
}
