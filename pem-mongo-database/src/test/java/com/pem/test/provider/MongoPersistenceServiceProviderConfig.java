package com.pem.test.provider;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.pem.persistence.api.provider.PersistenceServiceProvider;
import com.pem.persistence.mongo.provider.MongoPersistenceServiceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongoPersistenceServiceProviderConfig {

    @Bean
    public Mongo mongo() {
        Fongo queued = new Fongo("something");
        return queued.getMongo();
    }

    @Bean("testMongoFactory")
    public MongoDbFactory testMongoFactory() {
        return new SimpleMongoDbFactory(mongo(), "demo-test");
    }

    @Bean
    public PersistenceServiceProvider persistenceServiceProvider(){
        MongoPersistenceServiceProvider persistenceServiceProvider = new MongoPersistenceServiceProvider();
        persistenceServiceProvider.setMongoDbFactory(testMongoFactory());
        return persistenceServiceProvider;
    }
}
