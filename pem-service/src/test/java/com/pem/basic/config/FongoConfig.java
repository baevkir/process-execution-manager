package com.pem.basic.config;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.pem.persistence.service.operation.OperationService;
import com.pem.persistence.service.operation.OperationServiceImpl;
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
        public OperationService operationService() {
            return new OperationServiceImpl();
        }

}
