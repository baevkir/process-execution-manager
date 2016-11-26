package com.pem.persistence.repository.process;

import com.pem.persistence.model.proccess.record.ExecutionRecordEntity;
import com.pem.persistence.model.proccess.record.ExecutionRecordPK;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ExecutionRecordRepository extends MongoRepository<ExecutionRecordEntity, BigInteger> {
    ExecutionRecordEntity findExecutionRecordByPk(ExecutionRecordPK pk);
}
