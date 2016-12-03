package com.pem.test.launcher;

import com.pem.integration.launcher.ProcessExecutionManagerLauncher;
import com.pem.integration.launcher.ProcessExecutionManagerLauncherImpl;
import com.pem.core.operation.basic.Operation;
import com.pem.persistence.api.provider.PersistenceServiceProvider;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import com.pem.test.common.GlobalOperation;
import com.pem.test.common.config.PersistenceMockConfig;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Import(PersistenceMockConfig.class)
public class ProcessExecutionManagerContextLoaderConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @Scope("prototype")
    public Operation globalOperation() {
        return new GlobalOperation();
    }

    @Bean
    public PersistenceServiceProvider persistenceServiceProvider(){
        PersistenceServiceProvider persistenceServiceProvider = Mockito.mock(PersistenceServiceProvider.class);

        CalculatorPersistenceService calculatorPersistenceService = applicationContext.getBean(CalculatorPersistenceService.class);
        Mockito.when(persistenceServiceProvider.getCalculatorPersistenceService()).thenReturn(calculatorPersistenceService);

        OperationPersistenceService operationPersistenceService = applicationContext.getBean(OperationPersistenceService.class);
        Mockito.when(persistenceServiceProvider.getOperationPersistenceService()).thenReturn(operationPersistenceService);

        ExecutionRecordPersistenceService executionRecordPersistenceService = applicationContext.getBean(ExecutionRecordPersistenceService.class);
        Mockito.when(persistenceServiceProvider.getExecutionRecordPersistenceService()).thenReturn(executionRecordPersistenceService);

        ProcessPersistenceService processPersistenceService = applicationContext.getBean(ProcessPersistenceService.class);
        Mockito.when(persistenceServiceProvider.getProcessPersistenceService()).thenReturn(processPersistenceService);

        return persistenceServiceProvider;
    }

    @Bean
    public ProcessExecutionManagerLauncher processExecutionManagerContextLoader() {
        ProcessExecutionManagerLauncherImpl executionManagerProvider = new ProcessExecutionManagerLauncherImpl();
        executionManagerProvider.setPersistenceServiceProvider(persistenceServiceProvider());
        Map<String, String> beans = new HashMap<>();
        beans.put("testOperation", "globalOperation");
        executionManagerProvider.setParentBeans(beans);

        return executionManagerProvider;
    }
}
