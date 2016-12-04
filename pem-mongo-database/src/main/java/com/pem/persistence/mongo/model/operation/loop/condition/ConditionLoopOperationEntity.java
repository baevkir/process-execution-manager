package com.pem.persistence.mongo.model.operation.loop.condition;

import com.pem.persistence.mongo.model.calculator.bean.BinaryBeanCalculatorEntity;
import com.pem.persistence.mongo.model.operation.loop.LoopOperationEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class ConditionLoopOperationEntity extends LoopOperationEntity {

    @DBRef
    private BinaryBeanCalculatorEntity calculator;

    public BinaryBeanCalculatorEntity getCalculator() {
        return calculator;
    }

    public void setCalculator(BinaryBeanCalculatorEntity calculator) {
        this.calculator = calculator;
    }

    @Override
    public String toString() {
        return "ConditionLoopOperationEntity{" +
                "calculator=" + calculator +
                "} " + super.toString();
    }
}
