package com.pem.persistence.mongo.repository.process;

import com.pem.model.proccess.record.ExecutionRecordPK;
import com.pem.persistence.mongo.common.CommonMongoRepository;
import com.pem.persistence.mongo.model.proccess.record.ExecutionRecordEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionRecordRepository extends CommonMongoRepository<ExecutionRecordEntity> {
    ExecutionRecordEntity findExecutionRecordByPk(ExecutionRecordPK pk);
}
