package com.pem.model.calculator.bean;

import com.pem.model.calculator.common.CalculatorDTO;
import com.pem.model.common.bean.BeanObject;

public abstract class BeanCalculatorDTO extends CalculatorDTO {

    private BeanObject bean;

    public BeanObject getBean() {
        return bean;
    }

    public void setBean(BeanObject bean) {
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "BeanCalculatorDTO{" +
                "bean=" + bean +
                "} " + super.toString();
    }
}
