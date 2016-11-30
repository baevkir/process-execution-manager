package com.pem.persistence.repository.process;

import com.pem.persistence.common.CommonMongoRepository;
import com.pem.persistence.model.proccess.ExecutionProcessEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessRepository extends CommonMongoRepository<ExecutionProcessEntity> {
}
