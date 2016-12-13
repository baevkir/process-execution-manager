package com.pem.test.launcher;

import com.pem.core.operation.basic.Operation;
import com.pem.integration.launcher.ProcessExecutionManagerLauncher;
import com.pem.integration.launcher.ProcessExecutionManagerLauncherImpl;
import com.pem.persistence.api.provider.PersistenceServiceProvider;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import com.pem.test.common.GlobalOperation;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ProcessExecutionManagerContextLoaderConfig {

    @Bean
    @Scope("prototype")
    public Operation globalOperation() {
        return new GlobalOperation();
    }

    @Bean
    public PersistenceServiceProvider persistenceServiceProvider(){
        PersistenceServiceProvider persistenceServiceProvider = Mockito.mock(PersistenceServiceProvider.class);

        CalculatorPersistenceService calculatorPersistenceService = Mockito.mock(CalculatorPersistenceService.class);
        Mockito.when(persistenceServiceProvider.getCalculatorPersistenceService()).thenReturn(calculatorPersistenceService);

        OperationPersistenceService operationPersistenceService = Mockito.mock(OperationPersistenceService.class);
        Mockito.when(persistenceServiceProvider.getOperationPersistenceService()).thenReturn(operationPersistenceService);

        ExecutionRecordPersistenceService executionRecordPersistenceService = Mockito.mock(ExecutionRecordPersistenceService.class);
        Mockito.when(persistenceServiceProvider.getExecutionRecordPersistenceService()).thenReturn(executionRecordPersistenceService);

        ProcessPersistenceService processPersistenceService = Mockito.mock(ProcessPersistenceService.class);
        Mockito.when(persistenceServiceProvider.getProcessPersistenceService()).thenReturn(processPersistenceService);

        return persistenceServiceProvider;
    }

    @Bean
    public ProcessExecutionManagerLauncher processExecutionManagerContextLoader() {
        ProcessExecutionManagerLauncherImpl executionManagerProvider = new ProcessExecutionManagerLauncherImpl();
        executionManagerProvider.setPersistenceServiceProvider(persistenceServiceProvider());

        return executionManagerProvider;
    }
}
