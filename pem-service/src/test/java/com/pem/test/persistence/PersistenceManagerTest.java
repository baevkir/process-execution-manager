package com.pem.test.persistence;

import com.pem.model.operation.common.OperationObject;
import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.model.trigger.bean.BeanTriggerObject;
import com.pem.persistence.api.manager.PersistenceManager;
import com.pem.test.common.TestEntityCreator;
import com.pem.test.common.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.test.StepVerifier;

import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PersistenceManagerTest {

    @Autowired
    private PersistenceManager persistenceManager;

    private TestEntityCreator creator = new TestEntityCreator();

    @Test
    public void testBeanExist() {
        Assert.assertNotNull(persistenceManager);
    }

    @Test
    public void testCreateOperation() {
        StepVerifier.create(persistenceManager.create(creator.createSimpleBeanOperation()))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    public void testGetAllOperation() {
        StepVerifier.create(persistenceManager.getAll(OperationObject.class))
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    public void testGetAllByType() {
        StepVerifier.create(persistenceManager.getAllByType(BeanTriggerObject.class))
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    public void testNotImplementedOperations() {
        StepVerifier.create(persistenceManager.delete(BigInteger.ONE, ExecutionProcessObject.class))
                .expectError()
                .verify();
    }
}
