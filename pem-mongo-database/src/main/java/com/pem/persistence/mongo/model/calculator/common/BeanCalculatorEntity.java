package com.pem.persistence.mongo.model.calculator.common;

import com.pem.persistence.mongo.model.common.bean.BeanEntity;
import org.springframework.data.mongodb.core.index.Indexed;

public abstract class BeanCalculatorEntity extends CalculatorEntity {

    @Indexed(unique = true)
    private BeanEntity bean;

    public BeanEntity getBean() {
        return bean;
    }

    public void setBean(BeanEntity bean) {
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "BeanCalculatorEntity{" +
                "bean=" + bean +
                "} " + super.toString();
    }
}