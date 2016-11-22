package com.pem.test.loader;

import com.pem.integration.launcher.ProcessExecutionManagerLauncher;
import com.pem.integration.launcher.ProcessExecutionManagerLauncherImpl;
import com.pem.operation.basic.Operation;
import com.pem.test.common.GlobalOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProcessExecutionManagerContextLoaderConfig {

    @Bean
    @Scope("prototype")
    public Operation globalOperation() {
        return new GlobalOperation();
    }

    @Bean
    public ProcessExecutionManagerLauncher processExecutionManagerContextLoader() {
        ProcessExecutionManagerLauncherImpl executionManagerProvider = new ProcessExecutionManagerLauncherImpl();
        Map<String, String> beans = new HashMap<>();
        beans.put("testOperation", "globalOperation");
        executionManagerProvider.setBeans(beans);
        return executionManagerProvider;
    }
}
