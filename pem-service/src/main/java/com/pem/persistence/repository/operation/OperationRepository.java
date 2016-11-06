package com.pem.persistence.repository.operation;

import com.pem.persistence.model.operation.common.OperationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface OperationRepository extends MongoRepository<OperationEntity, BigInteger> {
}
