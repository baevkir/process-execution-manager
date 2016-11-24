package com.pem.test.persistence.service;

import com.pem.persistence.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.service.process.ProcessPersistenceService;
import com.pem.test.common.TestEntityCreator;
import com.pem.test.common.config.FongoConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FongoConfig.class)
public class ProcessorServiceTest {

    private TestEntityCreator creator = new TestEntityCreator();

    @Autowired
    private ProcessPersistenceService processPersistenceService;

    @Test
    public void testSaveToDBProcess() {
        ExecutionProcessEntity processEntity = new ExecutionProcessEntity();
        processEntity.setName("Test Process");
        processEntity.setExecutionOperation(creator.createBinaryConditionOperationEntity());
        processEntity.setExecutionRecords(Collections.EMPTY_LIST);
        ExecutionProcessEntity newProcessEntity = processPersistenceService.createProcess(processEntity);
        Assert.assertNotNull(newProcessEntity.getExecutionPlan());

        ExecutionProcessEntity queryProcess = processPersistenceService.getProcess(newProcessEntity.getId());
        Assert.assertNotNull(queryProcess.getExecutionOperation());
    }
}
