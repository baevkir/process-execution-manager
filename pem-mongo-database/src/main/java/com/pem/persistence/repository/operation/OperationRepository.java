package com.pem.persistence.repository.operation;

import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.common.CommonMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends CommonMongoRepository<OperationEntity> {
}
