package com.pem.model.operation.bean;

import com.pem.model.common.bean.BeanObject;
import com.pem.model.operation.common.OperationDTO;

public class BeanOperationDTO extends OperationDTO {

    private BeanObject bean;

    public BeanObject getBean() {
        return bean;
    }

    public void setBean(BeanObject bean) {
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "BeanOperationDTO{" +
                "bean=" + bean +
                "} " + super.toString();
    }
}
