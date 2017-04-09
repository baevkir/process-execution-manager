package com.pem.persistence.mongo.model.predicate.bean;

import com.pem.core.common.bean.BeanObject;
import com.pem.persistence.mongo.model.predicate.common.PredicateEntity;
import org.springframework.data.mongodb.core.index.Indexed;

public class BeanPredicateEntity extends PredicateEntity {

    @Indexed
    private BeanObject bean;

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BeanObject getBean() {
        return bean;
    }

    public void setBean(BeanObject bean) {
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "BeanPredicateEntity{" +
                "bean=" + bean +
                ", active=" + active +
                "} " + super.toString();
    }
}
