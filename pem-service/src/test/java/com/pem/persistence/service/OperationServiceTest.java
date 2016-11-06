package com.pem.persistence.service;

import com.pem.config.FongoConfig;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.persistence.model.operation.basic.BeanOperationEntity;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.operation.composite.CompositeOperationEntity;
import com.pem.persistence.model.operation.composite.SyncCompositeOperationEntity;
import com.pem.persistence.model.operation.condition.BinaryConditionOperationEntity;
import com.pem.persistence.model.operation.condition.ConditionOperationEntity;
import com.pem.persistence.model.operation.condition.state.BooleanState;
import com.pem.persistence.service.operation.OperationPersistenceService;
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
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FongoConfig.class)
public class OperationServiceTest {

    private Random random = new Random();

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
        compositeOperationEntity.setName("Test composite operation " + random.nextLong());
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
        BinaryConditionOperationEntity operation = (BinaryConditionOperationEntity) createBinaryConditionOperationEntity();
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

    private OperationEntity createSimpleBeanOperation() {
        BeanOperationEntity operationEntity = new BeanOperationEntity();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        BeanEntity bean = new BeanEntity();
        bean.setName("Sum Operation");
        bean.setBeanName("sumOperation");
        operationEntity.setBean(bean);

        return operationPersistenceService.createOperation(operationEntity);
    }

    private OperationEntity createBinaryConditionOperationEntity() {
        ConditionOperationEntity operationEntity = new BinaryConditionOperationEntity();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        operationEntity.setStates(Arrays.asList(createSimpleBinaryState(true), createSimpleBinaryState(false)));

        return operationPersistenceService.createOperation(operationEntity);
    }

    private BooleanState createSimpleBinaryState(Boolean value) {
        BooleanState state = new BooleanState();
        state.setConditionValue(value);
        state.setOperationEntity(createSimpleBeanOperation());

        return state;
    }
}
