package com.pem.ui.integration;

import com.vaadin.server.*;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.spring.server.SpringVaadinServletService;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class PemUIServlet extends SpringVaadinServlet {

    private static final String UI_PROVIDER_BEAN_PARAMETER = "uiProviderBean";
    private String uiProviderBeanName;

    @Override
    protected void servletInitialized() throws ServletException {
        getService().addSessionInitListener(new SessionInitListener() {

            private static final long serialVersionUID = -6307820453486668084L;

            @Override
            public void sessionInit(SessionInitEvent sessionInitEvent)
                    throws ServiceException {

                VaadinSession session = sessionInitEvent.getSession();
                List<UIProvider> uiProviders = new ArrayList<>(
                        session.getUIProviders());
                for (UIProvider provider : uiProviders) {
                    if (DefaultUIProvider.class.getCanonicalName().equals(
                            provider.getClass().getCanonicalName())) {
                        session.removeUIProvider(provider);
                    }
                }

                ApplicationContext context = getWebApplicationContext(session);
                UIProvider provider = context.getBean(uiProviderBeanName, UIProvider.class);
                session.addUIProvider(provider);
            }
        });
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        final ServletContext context = servletConfig.getServletContext();
        uiProviderBeanName = context.getInitParameter(UI_PROVIDER_BEAN_PARAMETER);
        Assert.hasText(uiProviderBeanName, "Parameer " + UI_PROVIDER_BEAN_PARAMETER + " undefined.");
    }

    protected WebApplicationContext getWebApplicationContext(VaadinSession session) {
        return ((SpringVaadinServletService) session.getService()).getWebApplicationContext();
    }
}
