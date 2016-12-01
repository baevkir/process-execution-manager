package com.pem.model.calculator.bean;

import com.pem.model.calculator.common.ConditionCalculatorDTO;
import com.pem.model.common.bean.BeanObject;

public abstract class BeanConditionCalculatorDTO extends ConditionCalculatorDTO {

    private BeanObject bean;

    public BeanObject getBean() {
        return bean;
    }

    public void setBean(BeanObject bean) {
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "BeanConditionCalculatorDTO{" +
                "bean=" + bean +
                "} " + super.toString();
    }
}
