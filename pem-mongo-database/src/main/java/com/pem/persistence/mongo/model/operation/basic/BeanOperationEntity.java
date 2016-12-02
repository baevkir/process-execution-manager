package com.pem.persistence.mongo.model.operation.basic;

import com.pem.model.common.bean.BeanObject;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import org.springframework.data.mongodb.core.index.Indexed;

public class BeanOperationEntity extends OperationEntity {

    @Indexed(unique = true)
    private BeanObject bean;

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
