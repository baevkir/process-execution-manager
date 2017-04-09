package com.pem.test.launcher;

import com.pem.integration.provider.PemServiceProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProcessExecutionManagerContextLoaderConfig.class)
public class ProcessExecutionManagerContextLoaderTest {

    @Autowired
    private PemServiceProvider serviceProvider;

    @Test
    public void processExecutionManagerContextLoaderTest() {
        Assert.assertNotNull(serviceProvider.getTriggerService());
        Assert.assertNotNull(serviceProvider.getOperationService());
        Assert.assertNotNull(serviceProvider.getExecutionProcessService());
    }
}
