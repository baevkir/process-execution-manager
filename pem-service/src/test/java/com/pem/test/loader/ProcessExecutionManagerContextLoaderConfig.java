package com.pem.test.loader;

import com.pem.integration.loader.ProcessExecutionManagerProvider;
import com.pem.integration.loader.ProcessExecutionManagerProviderImpl;
import com.pem.operation.basic.Operation;
import com.pem.test.common.GlobalOperation;
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
    public ProcessExecutionManagerProvider processExecutionManagerContextLoader() {
        ProcessExecutionManagerProvider executionManagerProvider = new ProcessExecutionManagerProviderImpl();
        return executionManagerProvider;
    }
}
