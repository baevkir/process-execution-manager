package com.pem.ui.presentation.common.navigator.impl;

import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.common.navigator.UINavigator;
import com.pem.ui.presentation.operation.list.OperationListViewImpl;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@SpringUI
public class UINavigatorImpl implements UINavigator {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationListViewImpl.class);
    private static final Pattern PARAMS_REGEXP = Pattern.compile("(\\w*)(=(\\w*))?");

    @Override
    public void navigate(NavigationParams params) {
        String path = params.getViewName() + "/" + createParamString(params.getUrlParams());
        UI.getCurrent().getNavigator().navigateTo(path);
    }

    @Override
    public Flux<NavigationParams> onViewChange() {
        return Flux.<ViewChangeListener.ViewChangeEvent>create(emitter -> addNavigationListener(emitter))
                .doOnNext(event -> LOGGER.debug("Handle event: {}.", event))
                .map(event -> mapToNavigationParams(event));
    }

    private NavigationParams mapToNavigationParams(ViewChangeListener.ViewChangeEvent event) {
        return NavigationParams.builder()
                .setViewName(event.getViewName())
                .setUrlParams(parseParams(event.getParameters()))
                .build();
    }

    private Map<String, String> parseParams(String paramsString) {
        HashMap<String, String> params = new HashMap<>();
        Arrays.stream(paramsString.split("&"))
                .filter(paramPair -> StringUtils.isNotEmpty(paramPair))
                .map(paramPair -> PARAMS_REGEXP.matcher(paramPair))
                .peek(matcher -> matcher.find())
                .peek(matcher -> LOGGER.debug("Find params matches param: {} value: {}.", matcher.group(1), matcher.group(3)))
                .forEach(matcher -> params.put(matcher.group(1), matcher.group(3)));

        return params;
    }

    private String createParamString(Map<String, String> params) {
        StringBuilder builder = new StringBuilder();

        params.entrySet().forEach(entry -> {
            LOGGER.debug("Append params: {}", entry);

            if (builder.length() != 0) {
                builder.append("&");
            }
            builder.append(entry.getKey());
            Optional.ofNullable(entry.getValue())
                    .ifPresent(value -> builder.append("=").append(value));
        });

        return builder.toString();
    }

    private void addNavigationListener(FluxSink<ViewChangeListener.ViewChangeEvent> emitter) {
        UI.getCurrent().getNavigator().addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                return !emitter.isCancelled();
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
                emitter.next(event);
            }
        });
    }

    private void navigateToPath(String path) {
        UI.getCurrent().getNavigator().navigateTo(path);
    }
}
