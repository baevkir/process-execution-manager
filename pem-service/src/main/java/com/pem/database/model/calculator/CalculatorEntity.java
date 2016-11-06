package com.pem.database.model.calculator;

import com.pem.database.model.common.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "calculators")
public abstract class CalculatorEntity<S> extends BaseEntity {

    @Override
    public String toString() {
        return "CalculatorEntity{} " + super.toString();
    }
}
