package com.pem.persistence.mongo.model.calculator.common;

import com.pem.persistence.mongo.model.common.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pem_calculators")
public abstract class CalculatorEntity<S> extends BaseEntity {

    @Override
    public String toString() {
        return "CalculatorEntity{} " + super.toString();
    }
}
