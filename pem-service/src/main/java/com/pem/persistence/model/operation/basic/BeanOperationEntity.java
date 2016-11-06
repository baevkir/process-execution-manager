package com.pem.persistence.model.operation.basic;

import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.persistence.model.operation.common.OperationEntity;

public class BeanOperationEntity extends OperationEntity {
    private BeanEntity bean;

    public BeanEntity getBean() {
        return bean;
    }

    public void setBean(BeanEntity bean) {
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "BeanOperationEntity{" +
                "bean=" + bean +
                "} " + super.toString();
    }
}
