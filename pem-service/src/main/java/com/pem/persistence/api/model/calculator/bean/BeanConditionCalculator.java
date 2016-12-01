package com.pem.persistence.api.model.calculator.bean;

import com.pem.persistence.api.model.calculator.common.ConditionCalculatorObject;
import com.pem.persistence.api.model.common.bean.BeanObject;
import org.springframework.data.mongodb.core.index.Indexed;

public abstract class BeanConditionCalculator extends ConditionCalculatorObject {

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
        return "BeanConditionCalculator{" +
                "bean=" + bean +
                "} " + super.toString();
    }
}
