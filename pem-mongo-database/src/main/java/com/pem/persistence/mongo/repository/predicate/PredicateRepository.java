package com.pem.persistence.mongo.repository.predicate;

import com.pem.persistence.mongo.common.CommonMongoRepository;
import com.pem.persistence.mongo.model.predicate.common.PredicateEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PredicateRepository extends CommonMongoRepository<PredicateEntity> {
}
