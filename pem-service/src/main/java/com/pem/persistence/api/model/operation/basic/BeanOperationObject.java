package com.pem.persistence.api.model.operation.basic;

import com.pem.persistence.api.model.common.bean.BeanObject;
import com.pem.persistence.api.model.operation.common.OperationObject;

public class BeanOperationObject extends OperationObject {

    private BeanObject bean;

    public BeanObject getBean() {
        return bean;
    }

    public void setBean(BeanObject bean) {
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "BeanOperationObject{" +
                "bean=" + bean +
                "} " + super.toString();
    }
}
