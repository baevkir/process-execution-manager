package com.pem.persistence.mongo.repository.operation;

import com.pem.persistence.mongo.common.CommonMongoRepository;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends CommonMongoRepository<OperationEntity> {
}
