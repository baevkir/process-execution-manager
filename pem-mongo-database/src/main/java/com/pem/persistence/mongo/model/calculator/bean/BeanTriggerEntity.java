package com.pem.persistence.mongo.model.calculator.bean;

import com.pem.core.common.applicationcontext.bean.BeanObject;
import com.pem.persistence.mongo.model.calculator.common.TriggerEntity;
import org.springframework.data.mongodb.core.index.Indexed;

public class BeanTriggerEntity extends TriggerEntity {

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
        return "BeanTriggerEntity{" +
                "bean=" + bean +
                ", active=" + active +
                "} " + super.toString();
    }
}
