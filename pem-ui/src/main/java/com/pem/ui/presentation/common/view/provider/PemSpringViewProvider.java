package com.pem.ui.presentation.common.view.provider;

import com.pem.model.common.BaseDTO;
import com.pem.ui.presentation.common.view.BeanFormView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringViewProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;

public class PemSpringViewProvider extends SpringViewProvider implements PemViewProvider {

    @Autowired
    public PemSpringViewProvider(ApplicationContext applicationContext, BeanDefinitionRegistry beanDefinitionRegistry) {
        super(applicationContext, beanDefinitionRegistry);
    }

    @Override
    public View getView(BaseDTO beanDTO) {
        String viewName = beanDTO.getClass().getCanonicalName();
        return getView(viewName);
    }

    @Override
    protected String getViewNameFromAnnotation(Class<?> beanClass, SpringView annotation) {
        BeanFormView beanFormViewAnnotation = beanClass.getAnnotation(BeanFormView.class);
        if (beanFormViewAnnotation != null) {
            return beanFormViewAnnotation.value().getCanonicalName();
        }
        return super.getViewNameFromAnnotation(beanClass, annotation);
    }
}
