package com.pem.ui.presentation.common.navigator.impl;

import com.pem.ui.presentation.common.navigator.NavigationManager;
import com.pem.ui.presentation.common.navigator.NavigationParams;
import com.pem.ui.presentation.common.view.provider.PemViewProvider;
import com.pem.ui.presentation.operation.list.OperationListViewImpl;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Optional;

@SpringUI
public class NavigationManagerImpl implements NavigationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationListViewImpl.class);

    private PemViewProvider viewProvider;

    private NavigationParams navigationParams;
    private PemNavigationStateManager stateManager;

    @Override
    public NavigationParams getNavigationParams() {
        return navigationParams;
    }

    @Override
    public void navigate(NavigationParams params) {
        boolean viewChanged = Optional.ofNullable(navigationParams)
                .map(currentParams -> !currentParams.getViewName().equals(params.getViewName()))
                .orElse(true);

        if (viewChanged) {
            UI.getCurrent().getNavigator().navigateTo(params.getPath());
        } else {
            UI.getCurrent().getPage().setUriFragment("!" + params.getPath(), true);
        }

        navigationParams = params;
    }

    @Override
    public Flux<NavigationParams> onNavigationChange() {
        return Flux.merge(onViewChange(), onUriChange())
                .distinctUntilChanged()
                .doOnNext(params -> LOGGER.debug("Fire Navigation Change with params {}.", params))
                .log();
    }

    @Override
    public void register(UI ui, ViewDisplay viewDisplay) {
        stateManager = new PemNavigationStateManager(ui.getPage());
        Navigator navigator = new Navigator(ui, stateManager, viewDisplay);
        navigator.addProvider(viewProvider);
        ui.setNavigator(navigator);

        if (StringUtils.isNotEmpty(stateManager.getState())) {
            navigate(NavigationParams.builder().setFromNavigationState(stateManager.getState()).build());
        }
    }

    private Flux<NavigationParams> onViewChange() {
        return Flux.<ViewChangeListener.ViewChangeEvent>create(emitter -> addNavigationListener(emitter))
                .distinctUntilChanged()
                .doOnNext(event -> LOGGER.debug("Handle event: {}.", event))
                .map(event -> mapToNavigationParams(event))
                .log().retry();
    }

    private Flux<NavigationParams> onUriChange() {
        return Flux.<Page.UriFragmentChangedEvent>create(emitter -> addUriChangeListener(emitter))
                .distinctUntilChanged()
                .doOnNext(event -> LOGGER.debug("Handle event: {}.", event))
                .map(event -> mapToNavigationParams(event))
                .log().retry();
    }
    private NavigationParams mapToNavigationParams(ViewChangeListener.ViewChangeEvent event) {
        return NavigationParams.builder()
                .setViewName(event.getViewName())
                .setFromNavigationParams(event.getParameters())
                .build();
    }

    private NavigationParams mapToNavigationParams(Page.UriFragmentChangedEvent event) {
        return NavigationParams.builder()
                .setFromNavigationState(stateManager.getState())
                .build();
    }

    private void addNavigationListener(FluxSink<ViewChangeListener.ViewChangeEvent> emitter) {
        UI.getCurrent().getNavigator().addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
               return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
                emitter.next(event);
            }
        });
    }

    private void addUriChangeListener(FluxSink<Page.UriFragmentChangedEvent> emitter) {
        UI.getCurrent().getPage().addUriFragmentChangedListener(event -> emitter.next(event));
    }

    class PemNavigationStateManager extends Navigator.UriFragmentManager {
        PemNavigationStateManager(Page page) {
            super(page);
        }

        @Override
        public void uriFragmentChanged(Page.UriFragmentChangedEvent event) {
            NavigationParams currentParams = NavigationParams.builder().setFromNavigationState(getState()).build();
            if (navigationParams == null
                    || !StringUtils.equals(currentParams.getViewName(), navigationParams.getViewName())) {
                super.uriFragmentChanged(event);
            }
            navigationParams = currentParams;
        }

    }

    @Autowired
    public void setViewProvider(PemViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }
}
