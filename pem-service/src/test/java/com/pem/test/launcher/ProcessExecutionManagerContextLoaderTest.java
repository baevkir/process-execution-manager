package com.pem.test.launcher;

import com.pem.integration.launcher.ProcessExecutionManagerLauncher;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.service.operation.OperationService;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProcessExecutionManagerContextLoaderConfig.class)
public class ProcessExecutionManagerContextLoaderTest {

    @Autowired
    public ProcessExecutionManagerLauncher contextLoader;

    @Test
    public void processExecutionManagerContextLoaderTest() {
        Assert.assertNotNull(contextLoader.getConditionCalculatorService());
        Assert.assertNotNull(contextLoader.getExecutionProcessService());
        Assert.assertNotNull(contextLoader.getOperationExecutor());
        Assert.assertNotNull(contextLoader.getOperationService());

        OperationService operationService = contextLoader.getOperationService();
        List<BeanEntity> operations = operationService.getAllOperationBeanEntities();

        Assert.assertTrue(CollectionUtils.isNotEmpty(operations));
    }
}
