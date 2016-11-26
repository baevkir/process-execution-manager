package com.pem.test.common;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import com.pem.persistence.service.calculator.MongoCalculatorPersistenceService;
import com.pem.persistence.service.operation.MongoOperationPersistenceService;
import com.pem.persistence.service.process.MongoProcessPersistenceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.pem.persistence.repository")
@EnableMongoAuditing
public class FongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "demo-test";
    }

    @Bean
    public Mongo mongo() {
        Fongo queued = new Fongo("something");
        return queued.getMongo();
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.pem.persistence.repository";
    }

    @Bean
    public OperationPersistenceService operationPersistenceService() {
        return new MongoOperationPersistenceService();
    }

    @Bean
    public CalculatorPersistenceService calculatorPersistenceService() {
        return new MongoCalculatorPersistenceService();
    }

    @Bean
    public ProcessPersistenceService processPersistenceService() {
        return new MongoProcessPersistenceService();
    }
}
