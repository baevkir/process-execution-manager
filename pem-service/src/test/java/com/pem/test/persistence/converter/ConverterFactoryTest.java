package com.pem.test.persistence.converter;

import com.pem.operation.basic.Operation;
import com.pem.operation.composite.SyncCompositeOperation;
import com.pem.operation.condition.BinaryConditionOperation;
import com.pem.operation.condition.IntegerConditionOperation;
import com.pem.operation.loop.condition.DoWhileOperation;
import com.pem.operation.loop.condition.WhileOperation;
import com.pem.operation.loop.counter.CounterLoopOperation;
import com.pem.persistence.converter.ConverterFactory;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.operation.composite.SyncCompositeOperationEntity;
import com.pem.persistence.model.operation.condition.IntegerConditionOperationEntity;
import com.pem.persistence.model.operation.loop.CounterLoopOperationEntity;
import com.pem.persistence.model.operation.loop.condition.DoWhileLoopOperationEntity;
import com.pem.persistence.model.operation.loop.condition.WhileLoopOperationEntity;
import com.pem.persistence.model.proccess.ExecutionProcessEntity;
import com.pem.test.common.TestEntityCreator;
import com.pem.test.common.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ConverterFactoryTest {

    @Autowired
    private ConverterFactory converterFactory;

    private TestEntityCreator creator = new TestEntityCreator();

    @Test
    public void testConverterExist() {
        OperationEntity operationEntity = creator.createSimpleBeanOperation();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
    }

    @Test
    public void testConverterConditionOperationOperation() {
        OperationEntity operationEntity = creator.createBinaryConditionOperationEntity();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof BinaryConditionOperation);
    }

    @Test
    public void testConverterSyncCompositeOperation() {
        SyncCompositeOperationEntity operationEntity = creator.createSyncCompositeOperationEntity();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof SyncCompositeOperation);
    }

    @Test
    public void testConverterIntegerConditionOperation() {
        IntegerConditionOperationEntity operationEntity = creator.createIntegerConditionOperationEntity();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof IntegerConditionOperation);
    }

    @Test
    public void testCounterLoopOperationConverter() {
        CounterLoopOperationEntity operationEntity = new CounterLoopOperationEntity();
        operationEntity.setCount(100);
        operationEntity.setOperation(creator.createIntegerConditionOperationEntity());
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof CounterLoopOperation);
    }

    @Test
    public void testDoWhileOperationConverter() {
        DoWhileLoopOperationEntity operationEntity = new DoWhileLoopOperationEntity();
        operationEntity.setOperation(creator.createSyncCompositeOperationEntity());
        operationEntity.setCalculator(creator.createBinaryCalculator());
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof DoWhileOperation);
    }

    @Test
    public void testWhileOperationConverter() {
        WhileLoopOperationEntity operationEntity = new WhileLoopOperationEntity();
        operationEntity.setOperation(creator.createIntegerConditionOperationEntity());
        operationEntity.setCalculator(creator.createBinaryCalculator());
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof WhileOperation);
    }

    @Test
    public void testExecutionProcessConverter() {
        OperationEntity operationEntity = creator.createSyncCompositeOperationEntity();
        ExecutionProcessEntity processEntity = converterFactory.convert(operationEntity, ExecutionProcessEntity.class);
        Assert.assertNotNull(processEntity);
        Assert.assertTrue(processEntity instanceof ExecutionProcessEntity);
    }
}
