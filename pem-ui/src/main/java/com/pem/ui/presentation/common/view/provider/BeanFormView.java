package com.pem.ui.presentation.common.view.provider;

import com.pem.model.common.BaseObject;
import com.vaadin.spring.annotation.SpringView;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
@SpringView
public @interface BeanFormView {
    Class<? extends BaseObject> value();
}
