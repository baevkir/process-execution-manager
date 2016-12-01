package com.pem.persistence.mongo.repository.calculator;

import com.pem.persistence.mongo.common.CommonMongoRepository;
import com.pem.persistence.mongo.model.calculator.common.CalculatorEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculatorRepository extends CommonMongoRepository<CalculatorEntity> {
}
