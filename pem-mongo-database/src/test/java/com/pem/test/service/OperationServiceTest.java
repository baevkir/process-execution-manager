package com.pem.test.service;

import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.model.calculator.BinaryCalculator;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.operation.composite.CompositeOperationEntity;
import com.pem.persistence.model.operation.composite.SyncCompositeOperationEntity;
import com.pem.persistence.model.operation.condition.BinaryConditionOperationEntity;
import com.pem.persistence.model.operation.condition.state.BooleanState;
import com.pem.test.common.FongoConfig;
import com.pem.test.common.TestEntityCreator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FongoConfig.class)
public class OperationServiceTest {

    private TestEntityCreator creator = new TestEntityCreator();

    @Autowired
    private CalculatorPersistenceService calculatorPersistenceService;
    @Autowired
    private OperationPersistenceService operationPersistenceService;

    @Test
    public void testSaveToDBOperation() {
        OperationEntity operationEntity = createSimpleBeanOperation();
        OperationEntity queryOperation = operationPersistenceService.getOperation(operationEntity.getId());
        Assert.assertEquals(operationEntity.getClass(), queryOperation.getClass());
    }

    @Test
    public void testSaveCompositeToDBOperation() {
        CompositeOperationEntity compositeOperationEntity = new SyncCompositeOperationEntity();
        OperationEntity operation = createBinaryConditionOperationEntity();
        List<OperationEntity> operationEntities = new ArrayList<>();
        operationEntities.add(operation);
        operationEntities.add(createSimpleBeanOperation());
        compositeOperationEntity.setOperationEntities(operationEntities);

        OperationEntity newCompositeOperationEntity = operationPersistenceService.createOperation(compositeOperationEntity);
        OperationEntity queryOperation = operationPersistenceService.getOperation(newCompositeOperationEntity.getId());
        Assert.assertEquals(newCompositeOperationEntity.getClass(), queryOperation.getClass());
    }

    @Test
    public void testDeleteOperation() {
        BinaryConditionOperationEntity operation = createBinaryConditionOperationEntity();
        BigInteger id = operation.getId();

        List<BigInteger> innerOperationIds = new ArrayList<>();
        for (BooleanState state : operation.getStates()) {
            innerOperationIds.add(state.getOperationEntity().getId());
        }

        operationPersistenceService.deleteOperation(id);

        OperationEntity testOperation = operationPersistenceService.getOperation(id);
        Assert.assertNull(testOperation);

        for (BigInteger innerId : innerOperationIds) {
            OperationEntity innerOperation = operationPersistenceService.getOperation(innerId);
            Assert.assertNotNull(innerOperation);
        }

    }

    private BinaryConditionOperationEntity createBinaryConditionOperationEntity() {
        BinaryConditionOperationEntity operationEntity = new BinaryConditionOperationEntity();
        operationEntity.setName("Test operation.");
        operationEntity.setDescription("Test description.");

        operationEntity.setStates(Arrays.asList(createSimpleBinaryState(true), createSimpleBinaryState(false)));
        return (BinaryConditionOperationEntity) operationPersistenceService.createOperation(operationEntity);
    }

    private BooleanState createSimpleBinaryState(Boolean value) {
        BooleanState state = new BooleanState();
        state.setConditionValue(value);
        state.setOperationEntity(createSimpleBeanOperation());

        return state;
    }

    private OperationEntity createSimpleBeanOperation() {
        return operationPersistenceService.createOperation(creator.createSimpleBeanOperation());
    }

    private BinaryCalculator createBinaryCalculator() {
        return (BinaryCalculator)calculatorPersistenceService.createCalculator(creator.createBinaryCalculator());
    }

}
