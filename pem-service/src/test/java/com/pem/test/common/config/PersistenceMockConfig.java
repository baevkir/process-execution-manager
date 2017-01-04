package com.pem.test.common.config;

import com.pem.model.operation.common.OperationDTO;
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
        OperationPersistenceService operationPersistenceService = Mockito.mock(OperationPersistenceService.class);
        Mockito.when(operationPersistenceService.createOperation(Mockito.any(OperationDTO.class)))
                .thenReturn(Mockito.mock(OperationDTO.class));

        return operationPersistenceService;
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
