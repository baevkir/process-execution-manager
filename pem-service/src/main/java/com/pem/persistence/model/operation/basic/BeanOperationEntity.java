package com.pem.persistence.model.operation.basic;

import com.pem.persistence.model.common.bean.OperationBeanEntity;
import com.pem.persistence.model.operation.common.OperationEntity;

public class BeanOperationEntity extends OperationEntity {
    private OperationBeanEntity bean;

    public OperationBeanEntity getBean() {
        return bean;
    }

    public void setBean(OperationBeanEntity bean) {
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "BeanOperationEntity{" +
                "bean=" + bean +
                "} " + super.toString();
    }
}
