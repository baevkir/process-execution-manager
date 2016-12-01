package com.pem.test.converter;

import com.pem.operation.basic.Operation;
import com.pem.operation.condition.BinaryConditionOperation;
import com.pem.operation.condition.IntegerConditionOperation;
import com.pem.operation.loop.condition.DoWhileOperation;
import com.pem.operation.loop.condition.WhileOperation;
import com.pem.operation.loop.counter.CounterLoopOperation;
import com.pem.logic.converter.ConverterFactory;
import com.pem.persistence.api.model.operation.common.OperationObject;
import com.pem.persistence.api.model.operation.composite.SyncCompositeOperationObject;
import com.pem.persistence.api.model.operation.condition.IntegerConditionOperationObject;
import com.pem.persistence.api.model.operation.loop.CounterLoopOperationObject;
import com.pem.persistence.api.model.operation.loop.condition.DoWhileLoopOperationObject;
import com.pem.persistence.api.model.operation.loop.condition.WhileLoopOperationObject;
import com.pem.persistence.api.model.proccess.ExecutionProcess;
import com.pem.test.common.TestEntityCreator;
import com.pem.test.common.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ConverterFactoryTest {

    @Autowired
    private ConverterFactory converterFactory;

    private TestEntityCreator creator = new TestEntityCreator();

    @Test
    public void testConverterExist() {
        OperationObject operationEntity = creator.createSimpleBeanOperation();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
    }

    @Test
    public void testConverterConditionOperationOperation() {
        OperationObject operationEntity = creator.createBinaryConditionOperationEntity();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof BinaryConditionOperation);
    }

    @Test
    public void testConverterSyncCompositeOperation() {
        SyncCompositeOperationObject operationEntity = creator.createSyncCompositeOperationEntity();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof com.pem.operation.composite.SyncCompositeOperation);
    }

    @Test
    public void testConverterIntegerConditionOperation() {
        IntegerConditionOperationObject operationEntity = creator.createIntegerConditionOperationEntity();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof IntegerConditionOperation);
    }

    @Test
    public void testCounterLoopOperationConverter() {
        CounterLoopOperationObject operationEntity = new CounterLoopOperationObject();
        operationEntity.setCount(100);
        operationEntity.setOperation(creator.createIntegerConditionOperationEntity());
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof CounterLoopOperation);
    }

    @Test
    public void testDoWhileOperationConverter() {
        DoWhileLoopOperationObject operationEntity = new DoWhileLoopOperationObject();
        operationEntity.setOperation(creator.createSyncCompositeOperationEntity());
        operationEntity.setCalculator(creator.createBinaryCalculator());
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof DoWhileOperation);
    }

    @Test
    public void testWhileOperationConverter() {
        WhileLoopOperationObject operationEntity = new WhileLoopOperationObject();
        operationEntity.setOperation(creator.createIntegerConditionOperationEntity());
        operationEntity.setCalculator(creator.createBinaryCalculator());
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof WhileOperation);
    }

    @Test
    public void testExecutionProcessConverter() {
        OperationObject operationEntity = creator.createSyncCompositeOperationEntity();
        ExecutionProcess processEntity = converterFactory.convert(operationEntity, OperationObject.class, ExecutionProcess.class);
        Assert.assertNotNull(processEntity);
        Assert.assertTrue(processEntity instanceof ExecutionProcess);
    }
}
