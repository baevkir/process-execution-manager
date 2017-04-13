package com.pem.ui.presentation.common.navigator;

import reactor.core.publisher.Flux;

public interface UINavigator {
    void navigate(NavigationParams params);
    Flux<NavigationParams> onViewChange();
}
