package com.pem.ui.integration.uiprovider;

import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.internal.SpringViewDisplayPostProcessor;
import com.vaadin.spring.internal.SpringViewDisplayRegistrationBean;
import com.vaadin.spring.internal.UIID;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;
import com.vaadin.util.CurrentInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractSpringUIProvider extends UIProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSpringUIProvider.class);

    private final Map<String, Class<? extends UI>> pathToUIMap = new ConcurrentHashMap<>();
    private final Map<String, Class<? extends UI>> wildcardPathToUIMap = new ConcurrentHashMap<>();

    private ServletContext servletContext;

    protected abstract ApplicationContext getApplicationContext();

    @SuppressWarnings("unchecked")
    protected void detectUIs() {
        LOGGER.info("Checking the application context for Vaadin UIs");
        final String[] uiBeanNames = getApplicationContext().getBeanNamesForAnnotation(SpringUI.class);
        for (String uiBeanName : uiBeanNames) {
            Class<?> beanType = getApplicationContext().getType(uiBeanName);
            if (!UI.class.isAssignableFrom(beanType)) {
                continue;
            }
            LOGGER.info("Found Vaadin UI [{}]", beanType.getCanonicalName());
            final String path;
            String tempPath = deriveMappingForUI(uiBeanName);
            if (tempPath.length() > 0 && !tempPath.startsWith("/")) {
                path = "/".concat(tempPath);
            } else {
                // remove terminal slash from mapping
                path = tempPath.replaceAll("/$", "");
            }
            Class<? extends UI> existingBeanType = getUIByPath(path);
            if (existingBeanType != null) {
                throw new IllegalStateException(String.format("[%s] is already mapped to the path [%s]", existingBeanType.getCanonicalName(), path));
            }
            LOGGER.debug("Mapping Vaadin UI [{}] to path [{}]", beanType.getCanonicalName(), path);
            mapPathToUI(path, (Class<? extends UI>) beanType);
        }
    }

    /**
     * Derive the name (path) for a UI based on its annotation parameters.
     * <p>
     * If a path is given as a parameter for the annotation, it is used. An
     * empty string maps to the root context.
     *
     * @param uiBeanName name of the UI bean
     * @return path to map the UI to
     */
    protected String deriveMappingForUI(String uiBeanName) {
        SpringUI annotation = getApplicationContext().findAnnotationOnBean(uiBeanName, SpringUI.class);
        return resolvePropertyPlaceholders(annotation.path());
    }

    @Override
    public Class<? extends UI> getUIClass(
            UIClassSelectionEvent uiClassSelectionEvent) {
        final String path = extractUIPathFromRequest(
                uiClassSelectionEvent.getRequest());
        if (pathToUIMap.containsKey(path)) {
            return pathToUIMap.get(path);
        }

        for (Map.Entry<String, Class<? extends UI>> entry : wildcardPathToUIMap
                .entrySet()) {
            if (path.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }

        return null;
    }

    private String extractUIPathFromRequest(VaadinRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String path = pathInfo;
            final int indexOfBang = path.indexOf('!');
            if (indexOfBang > -1) {
                path = path.substring(0, indexOfBang);
            }

            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
            return path;
        }
        return "";
    }

    protected void mapPathToUI(String path, Class<? extends UI> uiClass) {
        if (path.endsWith("/*")) {
            wildcardPathToUIMap.put(path.substring(0, path.length() - 2),
                    uiClass);
        } else {
            pathToUIMap.put(path, uiClass);
        }
    }

    protected Class<? extends UI> getUIByPath(String path) {
        return pathToUIMap.get(path);
    }

    @Override
    public UI createInstance(UICreateEvent event) {
        final Class<UIID> key = UIID.class;
        final UIID identifier = new UIID(event);
        CurrentInstance.set(key, identifier);
        try {
            LOGGER.debug(
                    "Creating a new UI bean of class [{}] with identifier [{}]",
                    event.getUIClass().getCanonicalName(), identifier);
            UI ui = getApplicationContext().getBean(event.getUIClass());
            configureNavigator(ui);
            return ui;
        } finally {
            CurrentInstance.set(key, null);
        }
    }

    /**
     * Create theme directory in the servlet context (if possible) to support
     * caching of themes compiled on the fly also in applications deployed as
     * JARs.
     * <p>
     * Any errors are logged and otherwise ignored, as this only helps caching.
     *
     * @param theme name of the theme
     */
    protected void createThemeDirectory(String theme) {
        File path = null;
        try {
            // support caching of compiled SCSS in applications packaged as JARs
            ServletContext servletContext = getServletContext();
            if (servletContext != null) {
                String root = servletContext.getRealPath("/");
                if (root != null && new File(root).isDirectory()) {
                    path = new File(servletContext
                            .getRealPath("/VAADIN/themes/" + theme));
                    LOGGER.debug("Creating directory [{}]", path);
                    path.mkdirs();
                }
            }
        } catch (Exception e) {
            LOGGER.info(
                    "Unable to create the directory [{}] for caching of themes compiled on the fly",
                    path);
        }
    }

    protected ServletContext getServletContext() {
        if (servletContext == null) {
            try {
                servletContext = getApplicationContext().getBean(ServletContext.class);
            } catch (NoSuchBeanDefinitionException e) {
                // any further error handling is done by callers as this is
                // optional for some uses
                LOGGER.debug("Unable to access the servlet context", e);
                return null;
            }
        }
        return servletContext;
    }

    /**
     * Configures a UI to use the navigator found by {@link #getNavigator()} if
     * there is a {@link SpringViewDisplay} annotation.
     *
     * @param ui the Spring managed UI instance for which to configure
     *           automatic navigation
     */
    protected void configureNavigator(UI ui) {
        // this test first as it is cheaper than looking for SpringViewDisplays
        // in case the backup mechanism would be used
        SpringNavigator navigator = getNavigator();
        if (navigator == null) {
            return;
        }
        Object springViewDisplay = findSpringViewDisplay(ui);
        if (springViewDisplay == null) {
            return;
        }

        if (springViewDisplay instanceof ViewDisplay) {
            navigator.init(ui, (ViewDisplay) springViewDisplay);
        } else if (springViewDisplay instanceof SingleComponentContainer) {
            navigator.init(ui, (SingleComponentContainer) springViewDisplay);
        } else if (springViewDisplay instanceof ComponentContainer) {
            navigator.init(ui, (ComponentContainer) springViewDisplay);
        } else {
            LOGGER.error("View display does not implement ViewDisplay/SingleComponentContainer/ComponentContainer: {}.", springViewDisplay);
            throw new IllegalStateException("View display does not implement ViewDisplay/SingleComponentContainer/ComponentContainer: " + springViewDisplay);
        }
    }

    /**
     * Returns the configured navigator bean or null if no bean defined.
     *
     * @return bean extending {@link SpringNavigator} or null if none defined
     * @throws BeansException if there are multiple navigator beans or other configuration
     *                        problem
     */
    protected SpringNavigator getNavigator() {
        try {
            return getApplicationContext().getBean(SpringNavigator.class);
        } catch (NoUniqueBeanDefinitionException e) {
            throw e;
        } catch (NoSuchBeanDefinitionException e) {
            // While relying on exceptions here is not very nice, using
            // getBean(Class) takes scopes, qualifiers etc. into account
            // consistently.
            // This is somewhat noisy as logged for every UI created.
            LOGGER.info("No Vaadin navigator bean defined");
            return null;
        }
    }

    protected Object findSpringViewDisplay(UI ui) {
        try {
            SpringViewDisplayRegistrationBean springViewDisplayRegistration = getApplicationContext().getBean(SpringViewDisplayRegistrationBean.class);
            return springViewDisplayRegistration.getSpringViewDisplay(getApplicationContext());
        } catch (NoUniqueBeanDefinitionException e) {
            throw e;
        } catch (NoSuchBeanDefinitionException e) {
            // fallback with getBeanNamesForAnnotation()
            LOGGER.debug("Looking for a SpringViewDisplay bean based on bean level annotations");

            final String[] springViewDisplayBeanNames = getApplicationContext().getBeanNamesForAnnotation(SpringViewDisplay.class);
            if (springViewDisplayBeanNames.length == 0) {
                LOGGER.debug("No view display defined for the UI " + ui.getId());
                return null;
            }
            if (springViewDisplayBeanNames.length > 1) {

                LOGGER.error("Multiple view displays defined for the UI "
                        + ui.getId() + ": "
                        + Arrays.toString(springViewDisplayBeanNames));

                throw new NoUniqueBeanDefinitionException(Object.class,
                        Arrays.asList(springViewDisplayBeanNames));
            }
            return getApplicationContext().getBean(springViewDisplayBeanNames[0]);
        }
    }

    protected SpringViewDisplayPostProcessor getSpringViewDisplayPostProcessor() {
        try {
            return getApplicationContext().getBean(SpringViewDisplayPostProcessor.class);
        } catch (NoUniqueBeanDefinitionException e) {
            LOGGER.error("Multiple " + SpringViewDisplayPostProcessor.class.getName() + " beans exist");
            throw e;
        } catch (NoSuchBeanDefinitionException e) {
            // This is somewhat noisy as potentially logged for every UI
            // created.
            LOGGER.info(SpringViewDisplayPostProcessor.class.getName() + " is not active");
            return null;
        }
    }

    @Override
    public String getTheme(UICreateEvent event) {
        String theme = super.getTheme(event);
        if (theme != null) {
            theme = resolvePropertyPlaceholders(theme);

            // optionally create a directory for cached themes
            createThemeDirectory(theme);
        }
        return theme;
    }

    @Override
    public String getPageTitle(UICreateEvent event) {
        String pageTitle = super.getPageTitle(event);
        if (pageTitle != null) {
            pageTitle = resolvePropertyPlaceholders(pageTitle);
        }
        return pageTitle;
    }

    private String resolvePropertyPlaceholders(String value) {
        if (StringUtils.hasText(value)) {
            return getApplicationContext().getEnvironment().resolvePlaceholders(value);
        }
        return value;
    }

}
