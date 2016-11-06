package com.pem.persistence.model.calculator.common;

import com.pem.persistence.model.common.bean.BeanEntity;

public abstract class BeanCalculatorEntity extends CalculatorEntity {

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
