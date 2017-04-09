package com.pem.model.trigger.bean;

import com.pem.core.common.bean.BeanObject;
import com.pem.model.trigger.common.TriggerObject;

public class BeanTriggerObject extends TriggerObject {

    private BeanObject bean;

    public BeanObject getBean() {
        return bean;
    }

    public void setBean(BeanObject bean) {
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "BeanTriggerObject{" +
                "bean=" + bean +
                "} " + super.toString();
    }
}