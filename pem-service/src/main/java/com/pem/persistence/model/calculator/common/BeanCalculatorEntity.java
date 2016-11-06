package com.pem.persistence.model.calculator.common;

import com.pem.persistence.model.common.bean.OperationBeanEntity;

public class BeanCalculatorEntity extends CalculatorEntity {

    private OperationBeanEntity beanEntity;

    public OperationBeanEntity getBeanEntity() {
        return beanEntity;
    }

    public void setBeanEntity(OperationBeanEntity beanEntity) {
        this.beanEntity = beanEntity;
    }

    @Override
    public String toString() {
        return "BeanCalculatorEntity{" +
                "beanEntity=" + beanEntity +
                "} " + super.toString();
    }
}
