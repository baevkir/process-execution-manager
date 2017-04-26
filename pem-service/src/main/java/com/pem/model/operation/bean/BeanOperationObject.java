package com.pem.model.operation.bean;

import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.bean.HasBeanObject;
import com.pem.model.operation.common.OperationObject;

public class BeanOperationObject extends OperationObject implements HasBeanObject {

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
