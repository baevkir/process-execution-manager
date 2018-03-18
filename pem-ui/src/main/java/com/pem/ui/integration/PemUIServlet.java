package com.pem.ui.integration;

import com.pem.ui.integration.uiprovider.PemUIProvider;
import com.vaadin.server.*;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.spring.server.SpringVaadinServletService;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class PemUIServlet extends SpringVaadinServlet {

    private static final String UI_PROVIDER_BEAN_PARAMETER = "uiProviderBean";
    private String uiProviderBeanName;

    @Override
    protected void servletInitialized() throws ServletException {
        getService().addSessionInitListener(sessionInitEvent -> {
            VaadinSession session = sessionInitEvent.getSession();
            List<UIProvider> uiProviders = new ArrayList<>(session.getUIProviders());
            for (UIProvider provider : uiProviders) {
                if (DefaultUIProvider.class.getCanonicalName().equals(provider.getClass().getCanonicalName())) {
                    session.removeUIProvider(provider);
                }
            }

            WebApplicationContext context = getWebApplicationContext(session);
            PemUIProvider provider = context.getBean(uiProviderBeanName, PemUIProvider.class);
            Assert.notNull(provider, "Can't find UIProvider.");
            session.addUIProvider(provider);
        });
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        uiProviderBeanName = servletConfig.getInitParameter(UI_PROVIDER_BEAN_PARAMETER);
        Assert.hasText(uiProviderBeanName, "Parameter " + UI_PROVIDER_BEAN_PARAMETER + " undefined.");
    }

    private WebApplicationContext getWebApplicationContext(VaadinSession session) {
        return ((SpringVaadinServletService) session.getService()).getWebApplicationContext();
    }
}
