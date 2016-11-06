package com.pem.persistence.model.calculator.common;

import com.pem.persistence.model.common.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "calculators")
public abstract class CalculatorEntity extends BaseEntity {

    @Override
    public String toString() {
        return "CalculatorEntity{} " + super.toString();
    }
}
