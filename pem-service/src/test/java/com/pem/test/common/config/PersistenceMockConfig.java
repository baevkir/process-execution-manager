package com.pem.test.common.config;

import com.pem.model.calculator.bean.BinaryBeanCalculatorDTO;
import com.pem.model.calculator.bean.IntegerBeanCalculatorDTO;
import com.pem.model.operation.bean.BeanOperationDTO;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Configuration
public class PersistenceMockConfig {

    @Bean
    public CalculatorPersistenceService calculatorPersistenceService() {
        CalculatorPersistenceService calculatorPersistenceService = Mockito.mock(CalculatorPersistenceService.class);

        Mockito.when(calculatorPersistenceService.getCalculatorsByType(BinaryBeanCalculatorDTO.class))
                .thenReturn(Flux.just(Mockito.mock(BinaryBeanCalculatorDTO.class)).repeat(10));

        Mockito.when(calculatorPersistenceService.getCalculatorsByType(IntegerBeanCalculatorDTO.class))
                .thenReturn(Flux.just(Mockito.mock(IntegerBeanCalculatorDTO.class)).repeat(10));

        return calculatorPersistenceService;
    }

    @Bean
    public OperationPersistenceService operationPersistenceService() {
        OperationPersistenceService operationPersistenceService = Mockito.mock(OperationPersistenceService.class);
        Mockito.when(operationPersistenceService.createOperation(Mockito.any(OperationDTO.class)))
                .thenReturn(Mono.just(Mockito.mock(OperationDTO.class)));

        Mockito.when(operationPersistenceService.deleteOperation(Mockito.any(BigInteger.class)))
                .thenReturn(Mono.empty());

        Mockito.when(operationPersistenceService.updateOperation(Mockito.any(OperationDTO.class)))
                .thenReturn(Mono.empty());

        Mockito.when(operationPersistenceService.getOperation(Mockito.any(BigInteger.class)))
                .thenReturn(Mono.just(Mockito.mock(OperationDTO.class)));

        Mockito.when(operationPersistenceService.getAllOperations())
                .thenReturn(Flux.just(Mockito.mock(OperationDTO.class)).repeat(10));

        Mockito.when(operationPersistenceService.getOperationsByType(BeanOperationDTO.class))
                .thenReturn(Flux.just(Mockito.mock(BeanOperationDTO.class)).repeat(10));

        return operationPersistenceService;
    }

    @Bean
    public ExecutionRecordPersistenceService executionRecordPersistenceService() {
        return Mockito.mock(ExecutionRecordPersistenceService.class);
    }

    @Bean
    public ProcessPersistenceService processPersistenceService() {
        ProcessPersistenceService persistenceService = Mockito.mock(ProcessPersistenceService.class);

        Mockito.when(persistenceService.updateProcess(Mockito.any(ExecutionProcessDTO.class)))
                .thenReturn(Mono.empty());

        Mockito.when(persistenceService.createProcess(Mockito.any(ExecutionProcessDTO.class)))
                .thenReturn(Mono.just(Mockito.mock(ExecutionProcessDTO.class)));

        Mockito.when(persistenceService.getProcess(Mockito.any(BigInteger.class)))
                .thenReturn(Mono.just(Mockito.mock(ExecutionProcessDTO.class)));

        Mockito.when(persistenceService.getAllProcesses())
                .thenReturn(Flux.just(Mockito.mock(ExecutionProcessDTO.class)).repeat(10));

        return persistenceService;
    }
}
