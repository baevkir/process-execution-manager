package com.pem.persistence.model.calculator.common;

import com.pem.persistence.model.common.bean.BeanEntity;

public abstract class BeanCalculatorEntity extends CalculatorEntity {

    private BeanEntity beanEntity;

    public BeanEntity getBeanEntity() {
        return beanEntity;
    }

    public void setBeanEntity(BeanEntity beanEntity) {
        this.beanEntity = beanEntity;
    }

    @Override
    public String toString() {
        return "BeanCalculatorEntity{" +
                "beanEntity=" + beanEntity +
                "} " + super.toString();
    }
}
