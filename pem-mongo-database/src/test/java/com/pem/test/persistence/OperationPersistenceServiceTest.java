package com.pem.test.persistence;

import com.pem.model.calculator.bean.BinaryBeanCalculatorDTO;
import com.pem.model.calculator.common.CalculatorDTO;
import com.pem.model.operation.bean.BeanOperationDTO;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.composite.CompositeOperationDTO;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.model.operation.condition.BinaryConditionOperationDTO;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.test.common.FongoConfig;
import com.pem.test.common.TestEntityCreator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FongoConfig.class)
public class OperationPersistenceServiceTest {

    private TestEntityCreator creator = new TestEntityCreator();

    @Autowired
    private CalculatorPersistenceService calculatorPersistenceService;
    @Autowired
    private OperationPersistenceService operationPersistenceService;

    @Test
    public void testSaveToDBOperation() {
        long operationsSize = operationPersistenceService.getAllOperations().count().block();
        SyncCompositeOperationDTO operationEntity1 = new SyncCompositeOperationDTO();
        operationEntity1.setOperations(Collections.singletonList(createSimpleBeanOperation()));
        OperationDTO queryOperation1 = operationPersistenceService.getOperation(
                operationPersistenceService.createOperation(operationEntity1).block().getId()).block();

        Assert.assertEquals(operationEntity1.getClass(), queryOperation1.getClass());

        SyncCompositeOperationDTO operationEntity2 = new SyncCompositeOperationDTO();
        operationEntity2.setOperations(Collections.singletonList(createSimpleBeanOperation()));
        OperationDTO queryOperation2 = operationPersistenceService.getOperation(
                operationPersistenceService.createOperation(operationEntity2).block().getId()).block();

        Assert.assertEquals(operationEntity2.getClass(), queryOperation2.getClass());

        long operationsSizeAfter = operationPersistenceService.getAllOperations().count().block();

        Assert.assertNotEquals(queryOperation2.getId(), queryOperation1.getId());
        Assert.assertEquals(operationsSizeAfter, operationsSize + 4);
    }

    @Test
    public void testSaveCompositeToDBOperation() {
        CompositeOperationDTO compositeOperationEntity = new SyncCompositeOperationDTO();
        OperationDTO operation = createBinaryConditionOperationEntity();
        List<OperationDTO> operationEntities = new ArrayList<>();
        operationEntities.add(operation);
        operationEntities.add(createSimpleBeanOperation());
        compositeOperationEntity.setOperations(operationEntities);

        OperationDTO newCompositeOperationEntity = operationPersistenceService.createOperation(compositeOperationEntity).block();
        OperationDTO queryOperation = operationPersistenceService.getOperation(newCompositeOperationEntity.getId()).block();
        Assert.assertEquals(newCompositeOperationEntity.getClass(), queryOperation.getClass());
    }

    @Test
    public void testDeleteOperation() {
        BinaryConditionOperationDTO operation = createBinaryConditionOperationEntity();
        BigInteger id = operation.getId();

        List<BigInteger> innerOperationIds = new ArrayList<>();
        for (Map.Entry<Boolean, OperationDTO> state : operation.getStates().entrySet()) {
            innerOperationIds.add(state.getValue().getId());
        }

        operationPersistenceService.deleteOperation(id).block();

        StepVerifier.create(operationPersistenceService.getOperation(id))
                .expectNextCount(0)
                .expectComplete()
                .verify();

        for (BigInteger innerId : innerOperationIds) {
            OperationDTO innerOperation = operationPersistenceService.getOperation(innerId).block();
            Assert.assertNotNull(innerOperation);

            operationPersistenceService.deleteOperation(innerOperation.getId()).block();

            StepVerifier.create(operationPersistenceService.getOperation(innerId))
                    .expectNextCount(1)
                    .expectComplete()
                    .verify();
        }

    }

    @Test
    public void testGetByImplementationOperation() {
        createSimpleBeanOperation();
        createSimpleBeanOperation();
        createBinaryConditionOperationEntity();

        List<OperationDTO> fullList = operationPersistenceService.getAllOperations().collectList().block();
        Assert.assertTrue(fullList.size() != 0);

        List<BeanOperationDTO> beanOperationEntities = operationPersistenceService.getOperationsByType(BeanOperationDTO.class).collectList().block();
        Assert.assertTrue(beanOperationEntities.size() != 0);
        Assert.assertTrue(beanOperationEntities.size() != fullList.size());

        List<BinaryConditionOperationDTO> conditionOperationEntities = operationPersistenceService.getOperationsByType(BinaryConditionOperationDTO.class).collectList().block();
        Assert.assertTrue(conditionOperationEntities.size() != 0);
        Assert.assertTrue(conditionOperationEntities.size() != fullList.size());
    }

    @Test
    public void updateOperationTest() {
        BeanOperationDTO beanOperationDTO = createSimpleBeanOperation();
        boolean oldStatus = beanOperationDTO.isActive();
        String oldName = beanOperationDTO.getName();
        String oldDescription = beanOperationDTO.getDescription();

        beanOperationDTO.setActive(!oldStatus);
        beanOperationDTO.setName(oldName + " new");
        beanOperationDTO.setDescription(oldDescription + " new");
        operationPersistenceService.updateOperation(beanOperationDTO).subscribe();
        BeanOperationDTO queryBeanOperation = (BeanOperationDTO) operationPersistenceService.getOperation(beanOperationDTO.getId()).block();

        Assert.assertNotEquals(oldName, queryBeanOperation.getName());
        Assert.assertNotEquals(oldDescription, queryBeanOperation.getDescription());
        Assert.assertNotEquals(oldStatus, queryBeanOperation.isActive());
    }

    private BinaryConditionOperationDTO createBinaryConditionOperationEntity() {
        BinaryConditionOperationDTO operationEntity = new BinaryConditionOperationDTO();
        operationEntity.setName("Test operation.");
        operationEntity.setDescription("Test description.");

        CalculatorDTO calculator = calculatorPersistenceService.createCalculator(creator.createRandomBinaryCalculator()).block();
        operationEntity.setCalculator(calculator);

        Map<Boolean, OperationDTO> states = new HashMap<>();
        states.putAll(createSimpleBinaryState(true));
        states.putAll(createSimpleBinaryState(false));
        operationEntity.setStates(states);

        return (BinaryConditionOperationDTO) operationPersistenceService.createOperation(operationEntity).block();
    }

    private Map<Boolean, OperationDTO> createSimpleBinaryState(Boolean value) {
        Map<Boolean, OperationDTO> state = new HashMap<>();
        state.put(value, createSimpleBeanOperation());
        return state;
    }

    private BeanOperationDTO createSimpleBeanOperation() {
        return (BeanOperationDTO) operationPersistenceService.createOperation(creator.createRandomSimpleBeanOperation()).block();
    }

    private BinaryBeanCalculatorDTO createBinaryCalculator() {
        return (BinaryBeanCalculatorDTO) calculatorPersistenceService.createCalculator(creator.createRandomBinaryCalculator()).block();
    }

}
