package com.pem.persistence.mongo.model.operation.loop.condition;

import com.pem.persistence.mongo.model.calculator.BinaryCalculator;
import com.pem.persistence.mongo.model.operation.loop.LoopOperationEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class ConditionLoopOperationEntity extends LoopOperationEntity {

    @DBRef
    private BinaryCalculator calculator;

    public BinaryCalculator getCalculator() {
        return calculator;
    }

    public void setCalculator(BinaryCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public String toString() {
        return "ConditionLoopOperationEntity{" +
                "calculator=" + calculator +
                "} " + super.toString();
    }
}
