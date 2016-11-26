package com.pem.test.common.config;

import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceMockConfig {

    @Bean
    public CalculatorPersistenceService calculatorPersistenceService() {
        return Mockito.mock(CalculatorPersistenceService.class);
    }

    @Bean
    public OperationPersistenceService operationPersistenceService() {
        return Mockito.mock(OperationPersistenceService.class);
    }

    @Bean
    public ExecutionRecordPersistenceService executionRecordPersistenceService() {
        return Mockito.mock(ExecutionRecordPersistenceService.class);
    }
    @Bean
    public ProcessPersistenceService processPersistenceService() {
        return Mockito.mock(ProcessPersistenceService.class);
    }
}
