package com.pem.ui.integration.viewprovider;

import com.pem.model.common.BaseObject;
import com.pem.model.operation.common.OperationObject;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringViewProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Set;

public class PemSpringViewProvider extends SpringViewProvider implements PemViewProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(PemSpringViewProvider.class);
    private final Set<OperationViewObject> operationViews = new HashSet<>();

    @Autowired
    public PemSpringViewProvider(ApplicationContext applicationContext, BeanDefinitionRegistry beanDefinitionRegistry) {
        super(applicationContext, beanDefinitionRegistry);
    }

    @Override
    public View getView(Class<? extends BaseObject> beanDTOType) {
        String viewName = beanDTOType.getCanonicalName();
        return getView(viewName);
    }

    @Override
    protected String getViewNameFromAnnotation(Class<?> beanClass, SpringView annotation) {
        BeanFormView beanFormViewAnnotation = beanClass.getAnnotation(BeanFormView.class);
        if (beanFormViewAnnotation != null) {
            addOperationView(beanClass, beanFormViewAnnotation);
            return beanFormViewAnnotation.value().getCanonicalName();
        }
        return super.getViewNameFromAnnotation(beanClass, annotation);
    }

    @Override
    public Set<OperationViewObject> getOperationViews() {
        return operationViews;
    }

    private void addOperationView(Class<?> beanClass, BeanFormView beanFormViewAnnotation) {
        OperationView operationAnnotation = beanClass.getAnnotation(OperationView.class);
        if (operationAnnotation == null) {
            return;
        }

        if (!View.class.isAssignableFrom(beanClass)) {
            LOGGER.warn("beanClass {} has BaseOperationView annotation but is not Assignable from View class!", beanClass);
            return;
        }

        if (beanFormViewAnnotation == null) {
            LOGGER.warn("beanClass {} has BaseOperationView annotation but don't have BeanView annotation!", beanClass);
            return;
        }

        Class<?> operationClass = beanFormViewAnnotation.value();
        if (!OperationObject.class.isAssignableFrom(operationClass)) {
            LOGGER.warn("beanClass {} has BaseOperationView annotation but BeanView annotation value is not Assignable from OperationObject class!", beanClass);
            return;
        }

        OperationViewObject operationViewObject = new OperationViewObject();
        operationViewObject.setName(operationAnnotation.value());
        operationViewObject.setOperationType((Class<? extends OperationObject>) operationClass);
        operationViews.add(operationViewObject);
    }
}
