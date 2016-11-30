package com.pem.persistence.repository.calculator;

import com.pem.persistence.common.CommonMongoRepository;
import com.pem.persistence.model.calculator.common.CalculatorEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculatorRepository extends CommonMongoRepository<CalculatorEntity> {
}
