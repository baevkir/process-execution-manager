package com.pem.test.service;

import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.persistence.api.service.process.ProcessPersistenceService;
import com.pem.test.common.FongoConfig;
import com.pem.test.common.TestEntityCreator;
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
        ExecutionProcessDTO processEntity = new ExecutionProcessDTO();
        processEntity.setName("Test Process");
        processEntity.setExecutionOperation(creator.createBinaryConditionOperationEntity());
        processEntity.setExecutionRecords(Collections.EMPTY_LIST);
        ExecutionProcessDTO newProcessEntity = processPersistenceService.createProcess(processEntity);
        Assert.assertNotNull(newProcessEntity.getExecutionOperation());

        ExecutionProcessDTO queryProcess = processPersistenceService.getProcess(newProcessEntity.getId());
        Assert.assertNotNull(queryProcess.getExecutionOperation());
    }
}
