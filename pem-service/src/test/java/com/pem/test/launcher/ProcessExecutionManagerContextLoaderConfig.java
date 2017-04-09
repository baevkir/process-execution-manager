package com.pem.test.launcher;

import com.pem.core.operation.basic.Operation;
import com.pem.integration.provider.PemServiceProvider;
import com.pem.integration.provider.PemServiceProviderImpl;
import com.pem.persistence.api.manager.PersistenceManager;
import com.pem.persistence.api.provider.PemPersistenceServiceProvider;
import com.pem.test.common.GlobalTestOperation;
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
    private PersistenceManager persistenceManager;

    @Bean
    @Scope("prototype")
    public Operation globalOperation() {
        return new GlobalTestOperation();
    }

    @Bean
    public PemPersistenceServiceProvider persistenceServiceProvider(){
        PemPersistenceServiceProvider persistenceServiceProvider = Mockito.mock(PemPersistenceServiceProvider.class);
        Mockito.when(persistenceServiceProvider.getPersistenceManager()).thenReturn(persistenceManager);
        return persistenceServiceProvider;
    }

    @Bean
    public PemServiceProvider processExecutionManagerContextLoader() {
        PemServiceProviderImpl executionManagerProvider = new PemServiceProviderImpl();
        executionManagerProvider.setPersistenceServiceProvider(persistenceServiceProvider());

        return executionManagerProvider;
    }
}
