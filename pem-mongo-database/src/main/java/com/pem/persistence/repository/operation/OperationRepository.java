package com.pem.persistence.repository.operation;

import com.pem.persistence.model.operation.common.OperationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface OperationRepository extends MongoRepository<OperationEntity, BigInteger> {
    @Query("{'_class':?0}")
    List<OperationEntity> findByImplementation(String className);
}
