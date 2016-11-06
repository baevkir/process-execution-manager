package com.pem.persistence.model.operation.loop.condition;

import com.pem.persistence.model.calculator.BinaryCalculator;
import com.pem.persistence.model.operation.loop.LoopOperationEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class CondotionLoopOperationEntity extends LoopOperationEntity {

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
        return "CondotionLoopOperationEntity{" +
                "calculator=" + calculator +
                "} " + super.toString();
    }
}
