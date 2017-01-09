package com.pem.test.common.config;

import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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

        Mockito.when(operationPersistenceService.getOperation(Mockito.any(BigInteger.class)))
                .thenReturn(Mockito.mock(OperationDTO.class));

        List<OperationDTO> operations = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            operations.add(Mockito.mock(OperationDTO.class));
        }
        Mockito.when(operationPersistenceService.getAllOperations())
                .thenReturn(operations);

        return operationPersistenceService;
    }

    @Bean
    public ExecutionRecordPersistenceService executionRecordPersistenceService() {
        return Mockito.mock(ExecutionRecordPersistenceService.class);
    }

    @Bean
    public ProcessPersistenceService processPersistenceService() {
        ProcessPersistenceService persistenceService = Mockito.mock(ProcessPersistenceService.class);

        Mockito.when(persistenceService.createProcess(Mockito.any(ExecutionProcessDTO.class)))
                .thenReturn(Mockito.mock(ExecutionProcessDTO.class));

        Mockito.when(persistenceService.getProcess(Mockito.any(BigInteger.class)))
                .thenReturn(Mockito.mock(ExecutionProcessDTO.class));

        List<ExecutionProcessDTO> processes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            processes.add(Mockito.mock(ExecutionProcessDTO.class));
        }
        Mockito.when(persistenceService.getAllProcesses()).thenReturn(processes);

        return persistenceService;
    }
}
