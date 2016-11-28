package com.pem.test.provider;


import com.pem.common.bean.provider.operation.OperationProvider;
import com.pem.test.common.config.TestConfig;
import com.pem.operation.basic.Operation;
import com.pem.operation.composite.SyncCompositeOperation;
import com.pem.operation.condition.BinaryConditionOperation;
import com.pem.operation.condition.IntegerConditionOperation;
import com.pem.operation.loop.condition.DoWhileOperation;
import com.pem.operation.loop.condition.WhileOperation;
import com.pem.operation.loop.counter.CounterLoopOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OperationProviderTest {

    @Autowired
    private OperationProvider operationProvider;

    private List<Class<? extends Operation>> commonOperations = new ArrayList<>();

    @Before
    public void setUp() {
        commonOperations.add(SyncCompositeOperation.class);
        commonOperations.add(BinaryConditionOperation.class);
        commonOperations.add(IntegerConditionOperation.class);
        commonOperations.add(CounterLoopOperation.class);
        commonOperations.add(DoWhileOperation.class);
        commonOperations.add(WhileOperation.class);
    }

    @Test
    public void testBeanExist() {
        Assert.assertNotNull(operationProvider);
    }

    @Test
    public void testCreateOperation() {
        Operation operation = operationProvider.createCommonOperation(SyncCompositeOperation.class);
        Assert.assertNotNull(operation);
    }

    @Test
    public void testCreateDifferentOperation() {
        for (Class<? extends Operation> clazz : commonOperations) {
            createDifferentOperationTest(clazz);
        }
    }

    @Test
    public void testGetCommonOperation() {
        Operation operation = operationProvider.createOperation("sumOperation", Operation.class);
        Assert.assertNotNull(operation);
    }

    @Test
    public void testFindGlobalOperations() {
        Map<String, Operation> operations = operationProvider.getAllGlobalOperations();
        Assert.assertTrue(!operations.isEmpty());
    }

    private void createDifferentOperationTest(Class<? extends Operation> clazz) {
        Operation operation1 = operationProvider.createCommonOperation(clazz);
        Operation operation2 = operationProvider.createCommonOperation(clazz);
        Assert.assertTrue(operation1 != operation2);
        Assert.assertTrue(operation1.getClass().equals(operation2.getClass()));
    }
}
