package com.pem.test.converter;

import com.pem.core.operation.composite.SyncCompositeOperation;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.model.operation.loop.CounterLoopOperationDTO;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.condition.BinaryConditionOperation;
import com.pem.core.operation.condition.IntegerConditionOperation;
import com.pem.core.operation.loop.condition.DoWhileOperation;
import com.pem.core.operation.loop.condition.WhileOperation;
import com.pem.core.operation.loop.counter.CounterLoopOperation;
import com.pem.core.converter.factory.ConverterFactory;
import com.pem.model.operation.condition.IntegerConditionOperationDTO;
import com.pem.model.operation.loop.condition.DoWhileLoopOperationDTO;
import com.pem.model.operation.loop.condition.WhileLoopOperationObject;
import com.pem.model.proccess.ExecutionProcess;
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
        OperationDTO operationEntity = creator.createSimpleBeanOperation();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
    }

    @Test
    public void testConverterConditionOperationOperation() {
        OperationDTO operationEntity = creator.createBinaryConditionOperationEntity();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof BinaryConditionOperation);
    }

    @Test
    public void testConverterSyncCompositeOperation() {
        SyncCompositeOperationDTO operationEntity = creator.createSyncCompositeOperationEntity();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof SyncCompositeOperation);
    }

    @Test
    public void testConverterIntegerConditionOperation() {
        IntegerConditionOperationDTO operationEntity = creator.createIntegerConditionOperationEntity();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof IntegerConditionOperation);
    }

    @Test
    public void testCounterLoopOperationConverter() {
        CounterLoopOperationDTO operationEntity = new CounterLoopOperationDTO();
        operationEntity.setCount(100);
        operationEntity.setOperation(creator.createIntegerConditionOperationEntity());
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof CounterLoopOperation);
    }

    @Test
    public void testDoWhileOperationConverter() {
        DoWhileLoopOperationDTO operationEntity = new DoWhileLoopOperationDTO();
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
        OperationDTO operationEntity = creator.createSyncCompositeOperationEntity();
        ExecutionProcess processEntity = converterFactory.convert(operationEntity, OperationDTO.class, ExecutionProcess.class);
        Assert.assertNotNull(processEntity);
        Assert.assertTrue(processEntity instanceof ExecutionProcess);
    }
}
