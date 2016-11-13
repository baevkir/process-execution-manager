package com.pem.test.persistence.converter;

import com.pem.operation.basic.Operation;
import com.pem.operation.composite.SyncCompositeOperation;
import com.pem.operation.condition.BinaryConditionOperation;
import com.pem.operation.condition.IntegerConditionOperation;
import com.pem.persistence.converter.ConverterFactory;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.operation.composite.SyncCompositeOperationEntity;
import com.pem.persistence.model.operation.condition.IntegerConditionOperationEntity;
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
}
