package com.pem.basic.config;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.pem.persistence.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.service.calculator.CalculatorPersistenceServiceImpl;
import com.pem.persistence.service.operation.OperationPersistenceService;
import com.pem.persistence.service.operation.OperationPersistenceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.pem.persistence.repository")
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
        return new OperationPersistenceServiceImpl();
    }

    @Bean
    public CalculatorPersistenceService calculatorPersistenceService() {
        return new CalculatorPersistenceServiceImpl();
    }

}
