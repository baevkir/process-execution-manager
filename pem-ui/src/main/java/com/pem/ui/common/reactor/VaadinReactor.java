package com.pem.ui.common.reactor;

import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.ui.Button;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;


public class VaadinReactor {

    public static Flux<Button.ClickEvent> buttonClickPublisher(Button button) {
        Assert.notNull(button);
        return Flux.create(emitter -> button.addClickListener(event -> emitter.next(event)));
    }

    @SuppressWarnings("unchecked")
    public static <S> Flux<ClickableRenderer.RendererClickEvent<S>> clickableRendererPublisher(ClickableRenderer<S, ?> clickableRenderer) {
        Assert.notNull(clickableRenderer);
        return Flux.create(emitter -> clickableRenderer.addClickListener(event -> emitter.next(event)));
    }
}
