package com.pem.persistence.mongo.model.operation.bean;

import com.pem.core.common.bean.BeanObject;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import org.springframework.data.mongodb.core.index.Indexed;

public class BeanOperationEntity extends OperationEntity {

    @Indexed(unique = true)
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
        return "BeanOperationEntity{" +
                "bean=" + bean +
                "} " + super.toString();
    }
}
