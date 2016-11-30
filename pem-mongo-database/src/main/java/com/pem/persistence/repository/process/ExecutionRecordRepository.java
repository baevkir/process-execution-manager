package com.pem.persistence.repository.process;

import com.pem.persistence.common.CommonMongoRepository;
import com.pem.persistence.model.proccess.record.ExecutionRecordEntity;
import com.pem.persistence.model.proccess.record.ExecutionRecordPK;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionRecordRepository extends CommonMongoRepository<ExecutionRecordEntity> {
    ExecutionRecordEntity findExecutionRecordByPk(ExecutionRecordPK pk);
}
