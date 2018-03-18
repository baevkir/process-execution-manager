package com.pem.test.converter;

import com.pem.core.operation.composite.SyncCompositeOperation;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.model.operation.loop.CounterLoopOperationObject;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.condition.predicate.PredicateOperation;
import com.pem.core.operation.condition.trigger.TriggerOperation;
import com.pem.core.operation.loop.condition.DoWhileOperation;
import com.pem.core.operation.loop.condition.WhileOperation;
import com.pem.core.operation.loop.counter.CounterLoopOperation;
import com.pem.core.common.converter.ConverterFactory;
import com.pem.model.operation.condition.TriggerOperationObject;
import com.pem.model.operation.loop.condition.DoWhileLoopOperationObject;
import com.pem.model.operation.loop.condition.WhileLoopOperationObject;
import com.pem.model.proccess.ExecutionProcessObject;
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
        OperationObject operationEntity = creator.createPredicateOperation();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof PredicateOperation);
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
        TriggerOperationObject operationEntity = creator.createTriggerOperationEntity();
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof TriggerOperation);
    }

    @Test
    public void testCounterLoopOperationConverter() {
        CounterLoopOperationObject operationEntity = new CounterLoopOperationObject();
        operationEntity.setCount(100);
        operationEntity.setOperation(creator.createTriggerOperationEntity());
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof CounterLoopOperation);
    }

    @Test
    public void testDoWhileOperationConverter() {
        DoWhileLoopOperationObject operationEntity = new DoWhileLoopOperationObject();
        operationEntity.setOperation(creator.createSyncCompositeOperationEntity());
        operationEntity.setPredicate(creator.createPredicate());
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof DoWhileOperation);
    }

    @Test
    public void testWhileOperationConverter() {
        WhileLoopOperationObject operationEntity = new WhileLoopOperationObject();
        operationEntity.setOperation(creator.createTriggerOperationEntity());
        operationEntity.setPredicate(creator.createPredicate());
        Operation operation = converterFactory.convert(operationEntity, Operation.class);
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof WhileOperation);
    }

    @Test
    public void testExecutionProcessConverter() {
        OperationObject operationEntity = creator.createSyncCompositeOperationEntity();
        ExecutionProcessObject processEntity = converterFactory.convert(operationEntity, OperationObject.class, ExecutionProcessObject.class);
        Assert.assertNotNull(processEntity);
    }
}
