package com.pem.persistence.repository.calculator;

import com.pem.persistence.model.calculator.common.CalculatorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CalculatorRepository extends MongoRepository<CalculatorEntity, BigInteger> {
}
