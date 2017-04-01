package com.pem.ui.presentation.common.view;

import com.vaadin.navigator.View;
import reactor.core.publisher.Mono;


public interface BeanView<B> extends View {
    Mono<Void> bind(B bean);
}
