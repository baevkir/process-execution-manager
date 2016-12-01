package com.pem.persistence.mongo.repository.process;

import com.pem.persistence.mongo.common.CommonMongoRepository;
import com.pem.persistence.mongo.model.proccess.ExecutionProcessEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessRepository extends CommonMongoRepository<ExecutionProcessEntity> {
}
