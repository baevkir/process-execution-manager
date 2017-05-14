package com.pem.ui.common.binder;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;

public class PemBeanFieldGroup<T> extends BeanFieldGroup<T> {
    public PemBeanFieldGroup(Class<T> beanType) {
        super(beanType);
    }

    public void commitUnchecked() {
        try {
            commit();
        } catch (FieldGroup.CommitException exception) {
            throw new RuntimeException(exception);
        }
    }
}
