package com.pem.test.common.config;

import com.pem.core.common.bean.BeanObject;
import com.pem.model.operation.bean.BeanOperationObject;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.model.trigger.bean.BeanTriggerObject;
import com.pem.persistence.api.manager.PersistenceManager;
import com.pem.persistence.api.manager.impl.PersistenceManagerImpl;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.api.service.process.ExecutionRecordPersistenceService;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import com.pem.persistence.api.service.trigger.TriggerPersistenceService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Configuration
public class PersistenceMockConfig {

    @Bean("pem.persistenceManager")
    public PersistenceManager persistenceManager() {
        return new PersistenceManagerImpl();
    }

    @Bean
    public TriggerPersistenceService triggerPersistenceService() {
        TriggerPersistenceService triggerPersistenceService = Mockito.mock(TriggerPersistenceService.class);

        Mockito.when(triggerPersistenceService.getAllByType(BeanTriggerObject.class))
                .thenReturn(Flux.defer(() -> {
                    BeanTriggerObject operation = Mockito.mock(BeanTriggerObject.class);
                    Mockito.when(operation.getBean()).thenReturn(Mockito.mock(BeanObject.class));
                    return Flux.just(operation);
                }).repeat(10));

        return triggerPersistenceService;
    }

    @Bean
    public OperationPersistenceService operationPersistenceService() {
        OperationPersistenceService operationPersistenceService = Mockito.mock(OperationPersistenceService.class);
        Mockito.when(operationPersistenceService.create(Mockito.any(OperationObject.class)))
                .thenReturn(Mono.just(Mockito.mock(OperationObject.class)));

        Mockito.when(operationPersistenceService.delete(Mockito.any(BigInteger.class)))
                .thenReturn(Mono.empty());

        Mockito.when(operationPersistenceService.update(Mockito.any(OperationObject.class)))
                .thenReturn(Mono.empty());

        Mockito.when(operationPersistenceService.getById(Mockito.any(BigInteger.class)))
                .thenReturn(Mono.just(Mockito.mock(OperationObject.class)));

        Mockito.when(operationPersistenceService.getAll())
                .thenReturn(Flux.just(Mockito.mock(OperationObject.class)).repeat(10));

        Mockito.when(operationPersistenceService.getAllByType(BeanOperationObject.class))
                .thenReturn(Flux.defer(() -> {
                            BeanOperationObject operation = Mockito.mock(BeanOperationObject.class);
                            Mockito.when(operation.getBean()).thenReturn(Mockito.mock(BeanObject.class));
                            return Flux.just(operation);
                        }).repeat(10));


        return operationPersistenceService;
    }

    @Bean
    public ExecutionRecordPersistenceService executionRecordPersistenceService() {
        return Mockito.mock(ExecutionRecordPersistenceService.class);
    }

    @Bean
    public ProcessPersistenceService processPersistenceService() {
        ProcessPersistenceService persistenceService = Mockito.mock(ProcessPersistenceService.class);

        Mockito.when(persistenceService.update(Mockito.any(ExecutionProcessObject.class)))
                .thenReturn(Mono.empty());

        Mockito.when(persistenceService.create(Mockito.any(ExecutionProcessObject.class)))
                .thenReturn(Mono.just(Mockito.mock(ExecutionProcessObject.class)));

        Mockito.when(persistenceService.getById(Mockito.any(BigInteger.class)))
                .thenReturn(Mono.just(Mockito.mock(ExecutionProcessObject.class)));

        Mockito.when(persistenceService.getAll())
                .thenReturn(Flux.just(Mockito.mock(ExecutionProcessObject.class)).repeat(10));

        return persistenceService;
    }
}
