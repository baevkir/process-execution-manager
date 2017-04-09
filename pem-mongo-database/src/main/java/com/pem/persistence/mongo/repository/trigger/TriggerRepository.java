package com.pem.persistence.mongo.repository.trigger;

import com.pem.persistence.mongo.common.CommonMongoRepository;
import com.pem.persistence.mongo.model.calculator.common.TriggerEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TriggerRepository extends CommonMongoRepository<TriggerEntity> {
}
