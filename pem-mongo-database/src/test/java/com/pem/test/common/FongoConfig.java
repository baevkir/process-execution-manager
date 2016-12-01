package com.pem.test.common;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
@ImportResource("classpath:config/mongo-database-config.xml")
public class FongoConfig {

    @Bean
    public Mongo mongo() {
        Fongo queued = new Fongo("something");
        return queued.getMongo();
    }

    @Bean
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(mongo(), "test_db");
    }

}
