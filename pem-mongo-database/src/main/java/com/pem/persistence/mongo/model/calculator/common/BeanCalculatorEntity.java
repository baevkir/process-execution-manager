package com.pem.persistence.mongo.model.calculator.common;

import com.pem.model.common.bean.BeanObject;
import org.springframework.data.mongodb.core.index.Indexed;

public abstract class BeanCalculatorEntity extends CalculatorEntity {

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
        return "BeanCalculatorEntity{" +
                "bean=" + bean +
                "} " + super.toString();
    }
}
