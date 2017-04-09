package com.pem.model.predicate.bean;

import com.pem.core.common.bean.BeanObject;
import com.pem.model.predicate.common.PredicateObject;

public class BeanPredicateObject extends PredicateObject {

    private BeanObject bean;

    public BeanObject getBean() {
        return bean;
    }

    public void setBean(BeanObject bean) {
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "BeanPredicateObject{" +
                "bean=" + bean +
                "} " + super.toString();
    }
}
