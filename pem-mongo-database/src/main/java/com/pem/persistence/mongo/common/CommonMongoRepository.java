package com.pem.persistence.mongo.common;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface CommonMongoRepository<T> extends MongoRepository<T, BigInteger> {
    @Query("{'_class':?0}")
    List<T> findByImplementation(String className);
}
