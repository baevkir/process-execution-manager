package com.pem.persistence.repository.process;

import com.pem.persistence.model.proccess.ExecutionProcessEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ProcessRepository extends MongoRepository<ExecutionProcessEntity, BigInteger> {
}
