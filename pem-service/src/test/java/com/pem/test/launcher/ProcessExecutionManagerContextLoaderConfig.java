package com.pem.test.launcher;

import com.pem.core.operation.basic.Operation;
import com.pem.integration.provider.PemServiceProvider;
import com.pem.integration.provider.PemServiceProviderImpl;
import com.pem.persistence.api.provider.PemPersistenceServiceProvider;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import com.pem.test.common.GlobalOperation;
import com.pem.test.common.config.PersistenceMockConfig;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@Configuration
@Import(PersistenceMockConfig.class)
public class ProcessExecutionManagerContextLoaderConfig {

    @Autowired
    private CalculatorPersistenceService calculatorPersistenceService;

    @Autowired
    private OperationPersistenceService operationPersistenceService;

    @Autowired
    private ExecutionRecordPersistenceService executionRecordPersistenceService;

    @Autowired
    private ProcessPersistenceService processPersistenceService;

    @Bean
    @Scope("prototype")
    public Operation globalOperation() {
        return new GlobalOperation();
    }

    @Bean
    public PemPersistenceServiceProvider persistenceServiceProvider(){
        PemPersistenceServiceProvider persistenceServiceProvider = Mockito.mock(PemPersistenceServiceProvider.class);

        Mockito.when(persistenceServiceProvider.getCalculatorPersistenceService()).thenReturn(calculatorPersistenceService);

        Mockito.when(persistenceServiceProvider.getOperationPersistenceService()).thenReturn(operationPersistenceService);

        Mockito.when(persistenceServiceProvider.getExecutionRecordPersistenceService()).thenReturn(executionRecordPersistenceService);

        Mockito.when(persistenceServiceProvider.getProcessPersistenceService()).thenReturn(processPersistenceService);

        return persistenceServiceProvider;
    }

    @Bean
    public PemServiceProvider processExecutionManagerContextLoader() {
        PemServiceProviderImpl executionManagerProvider = new PemServiceProviderImpl();
        executionManagerProvider.setPersistenceServiceProvider(persistenceServiceProvider());

        return executionManagerProvider;
    }
}
