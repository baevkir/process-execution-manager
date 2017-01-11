package com.pem.test.service;

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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int operationsSize = operationPersistenceService.getAllOperations().size();
        OperationDTO operationEntity1 = createSimpleBeanOperation();
        OperationDTO queryOperation1 = operationPersistenceService.getOperation(operationEntity1.getId());
        Assert.assertEquals(operationEntity1.getClass(), queryOperation1.getClass());

        OperationDTO operationEntity2 = createSimpleBeanOperation();
        OperationDTO queryOperation2 = operationPersistenceService.getOperation(operationEntity2.getId());
        Assert.assertEquals(operationEntity2.getClass(), queryOperation2.getClass());

        int operationsSizeAfter = operationPersistenceService.getAllOperations().size();

        Assert.assertNotEquals(operationEntity1.getId(), operationEntity2.getId());
        Assert.assertEquals(operationsSizeAfter - operationsSize, 2);
    }

    @Test
    public void testSaveCompositeToDBOperation() {
        CompositeOperationDTO compositeOperationEntity = new SyncCompositeOperationDTO();
        OperationDTO operation = createBinaryConditionOperationEntity();
        List<OperationDTO> operationEntities = new ArrayList<>();
        operationEntities.add(operation);
        operationEntities.add(createSimpleBeanOperation());
        compositeOperationEntity.setOperations(operationEntities);

        OperationDTO newCompositeOperationEntity = operationPersistenceService.createOperation(compositeOperationEntity);
        OperationDTO queryOperation = operationPersistenceService.getOperation(newCompositeOperationEntity.getId());
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

        operationPersistenceService.deleteOperation(id);

        OperationDTO testOperation = operationPersistenceService.getOperation(id);
        Assert.assertNull(testOperation);

        for (BigInteger innerId : innerOperationIds) {
            OperationDTO innerOperation = operationPersistenceService.getOperation(innerId);
            Assert.assertNotNull(innerOperation);

            operationPersistenceService.deleteOperation(innerOperation.getId());

            Assert.assertNull(operationPersistenceService.getOperation(innerId));
        }

    }

    @Test
    public void testGetByImplementationOperation() {
        createSimpleBeanOperation();
        createSimpleBeanOperation();
        createBinaryConditionOperationEntity();

        List<OperationDTO> fullList = operationPersistenceService.getAllOperations();
        Assert.assertTrue(fullList.size() != 0);

        List<BeanOperationDTO> beanOperationEntities = operationPersistenceService.getOperationsByType(BeanOperationDTO.class);
        Assert.assertTrue(beanOperationEntities.size() != 0);
        Assert.assertTrue(beanOperationEntities.size() != fullList.size());

        List<BinaryConditionOperationDTO> conditionOperationEntities = operationPersistenceService.getOperationsByType(BinaryConditionOperationDTO.class);
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
        operationPersistenceService.updateOperation(beanOperationDTO);
        BeanOperationDTO queryBeanOperation = (BeanOperationDTO) operationPersistenceService.getOperation(beanOperationDTO.getId());

        Assert.assertNotEquals(oldName, queryBeanOperation.getName());
        Assert.assertNotEquals(oldDescription, queryBeanOperation.getDescription());
        Assert.assertNotEquals(oldStatus, queryBeanOperation.isActive());
    }

    private BinaryConditionOperationDTO createBinaryConditionOperationEntity() {
        BinaryConditionOperationDTO operationEntity = new BinaryConditionOperationDTO();
        operationEntity.setName("Test operation.");
        operationEntity.setDescription("Test description.");

        CalculatorDTO calculator = calculatorPersistenceService.createCalculator(creator.createRandomBinaryCalculator());
        operationEntity.setCalculator(calculator);

        Map<Boolean, OperationDTO> states = new HashMap<>();
        states.putAll(createSimpleBinaryState(true));
        states.putAll(createSimpleBinaryState(false));
        operationEntity.setStates(states);

        return (BinaryConditionOperationDTO) operationPersistenceService.createOperation(operationEntity);
    }

    private Map<Boolean, OperationDTO> createSimpleBinaryState(Boolean value) {
        Map<Boolean, OperationDTO> state = new HashMap<>();
        state.put(value, createSimpleBeanOperation());
        return state;
    }

    private BeanOperationDTO createSimpleBeanOperation() {
        return (BeanOperationDTO) operationPersistenceService.createOperation(creator.createRandomSimpleBeanOperation());
    }

    private BinaryBeanCalculatorDTO createBinaryCalculator() {
        return (BinaryBeanCalculatorDTO) calculatorPersistenceService.createCalculator(creator.createRandomBinaryCalculator());
    }

}
