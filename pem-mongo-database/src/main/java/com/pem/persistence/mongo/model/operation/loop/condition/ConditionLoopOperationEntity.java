package com.pem.persistence.mongo.model.operation.loop.condition;

import com.pem.persistence.mongo.model.calculator.common.CalculatorEntity;
import com.pem.persistence.mongo.model.operation.loop.LoopOperationEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class ConditionLoopOperationEntity extends LoopOperationEntity {

    @DBRef
    private CalculatorEntity<Boolean> calculator;

    public CalculatorEntity<Boolean> getCalculator() {
        return calculator;
    }

    public void setCalculator(CalculatorEntity<Boolean> calculator) {
        this.calculator = calculator;
    }

    @Override
    public String toString() {
        return "ConditionLoopOperationEntity{" +
                "calculator=" + calculator +
                "} " + super.toString();
    }
}
