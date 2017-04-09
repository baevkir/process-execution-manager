package com.pem.test.persistence;

import com.pem.model.operation.bean.BeanOperationObject;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.operation.composite.CompositeOperationDTO;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.model.operation.condition.PredicateOperationObject;
import com.pem.model.predicate.bean.BeanPredicateObject;
import com.pem.model.predicate.common.PredicateObject;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import com.pem.persistence.api.service.predicate.PredicatePersistenceService;
import com.pem.persistence.api.service.trigger.TriggerPersistenceService;
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
    private TriggerPersistenceService triggerPersistenceService;
    @Autowired
    private PredicatePersistenceService predicatePersistenceService;
    @Autowired
    private OperationPersistenceService operationPersistenceService;

    @Test
    public void testSaveToDBOperation() {
        long operationsSize = operationPersistenceService.getAll().count().block();
        SyncCompositeOperationDTO operationEntity1 = new SyncCompositeOperationDTO();
        operationEntity1.setOperations(Collections.singletonList(createSimpleBeanOperation()));
        OperationObject queryOperation1 = operationPersistenceService.getById(
                operationPersistenceService.create(operationEntity1).block().getId()).block();

        Assert.assertEquals(operationEntity1.getClass(), queryOperation1.getClass());

        SyncCompositeOperationDTO operationEntity2 = new SyncCompositeOperationDTO();
        operationEntity2.setOperations(Collections.singletonList(createSimpleBeanOperation()));
        OperationObject queryOperation2 = operationPersistenceService.getById(
                operationPersistenceService.create(operationEntity2).block().getId()).block();

        Assert.assertEquals(operationEntity2.getClass(), queryOperation2.getClass());

        long operationsSizeAfter = operationPersistenceService.getAll().count().block();

        Assert.assertNotEquals(queryOperation2.getId(), queryOperation1.getId());
        Assert.assertEquals(operationsSizeAfter, operationsSize + 4);
    }

    @Test
    public void testSaveCompositeToDBOperation() {
        CompositeOperationDTO compositeOperationEntity = new SyncCompositeOperationDTO();
        OperationObject operation = createBinaryConditionOperationEntity();
        List<OperationObject> operationEntities = new ArrayList<>();
        operationEntities.add(operation);
        operationEntities.add(createSimpleBeanOperation());
        compositeOperationEntity.setOperations(operationEntities);

        OperationObject newCompositeOperationEntity = operationPersistenceService.create(compositeOperationEntity).block();
        OperationObject queryOperation = operationPersistenceService.getById(newCompositeOperationEntity.getId()).block();
        Assert.assertEquals(newCompositeOperationEntity.getClass(), queryOperation.getClass());
    }

    @Test
    public void testDeleteOperation() {
        PredicateOperationObject operation = createBinaryConditionOperationEntity();
        BigInteger id = operation.getId();

        List<BigInteger> innerOperationIds = new ArrayList<>();
        for (Map.Entry<Boolean, OperationObject> state : operation.getStates().entrySet()) {
            innerOperationIds.add(state.getValue().getId());
        }

        operationPersistenceService.delete(id).block();

        StepVerifier.create(operationPersistenceService.getById(id))
                .expectNextCount(0)
                .expectComplete()
                .verify();

        for (BigInteger innerId : innerOperationIds) {
            OperationObject innerOperation = operationPersistenceService.getById(innerId).block();
            Assert.assertNotNull(innerOperation);

            operationPersistenceService.delete(innerOperation.getId()).block();

            StepVerifier.create(operationPersistenceService.getById(innerId))
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

        List<OperationObject> fullList = operationPersistenceService.getAll().collectList().block();
        Assert.assertTrue(fullList.size() != 0);

        List<BeanOperationObject> beanOperationEntities = operationPersistenceService.getAllByType(BeanOperationObject.class).collectList().block();
        Assert.assertTrue(beanOperationEntities.size() != 0);
        Assert.assertTrue(beanOperationEntities.size() != fullList.size());

        List<PredicateOperationObject> conditionOperationEntities = operationPersistenceService.getAllByType(PredicateOperationObject.class).collectList().block();
        Assert.assertTrue(conditionOperationEntities.size() != 0);
        Assert.assertTrue(conditionOperationEntities.size() != fullList.size());
    }

    @Test
    public void updateOperationTest() {
        BeanOperationObject beanOperationDTO = createSimpleBeanOperation();
        boolean oldStatus = beanOperationDTO.isActive();
        String oldName = beanOperationDTO.getName();
        String oldDescription = beanOperationDTO.getDescription();

        beanOperationDTO.setActive(!oldStatus);
        beanOperationDTO.setName(oldName + " new");
        beanOperationDTO.setDescription(oldDescription + " new");
        operationPersistenceService.update(beanOperationDTO).subscribe();
        BeanOperationObject queryBeanOperation = (BeanOperationObject) operationPersistenceService.getById(beanOperationDTO.getId()).block();

        Assert.assertNotEquals(oldName, queryBeanOperation.getName());
        Assert.assertNotEquals(oldDescription, queryBeanOperation.getDescription());
        Assert.assertNotEquals(oldStatus, queryBeanOperation.isActive());
    }

    private PredicateOperationObject createBinaryConditionOperationEntity() {
        PredicateOperationObject operationEntity = new PredicateOperationObject();
        operationEntity.setName("Test operation.");
        operationEntity.setDescription("Test description.");

        PredicateObject predicateObject = predicatePersistenceService.create(creator.createRandomPredicate()).block();
        operationEntity.setPredicate(predicateObject);

        Map<Boolean, OperationObject> states = new HashMap<>();
        states.putAll(createSimpleBinaryState(true));
        states.putAll(createSimpleBinaryState(false));
        operationEntity.setStates(states);

        return (PredicateOperationObject) operationPersistenceService.create(operationEntity).block();
    }

    private Map<Boolean, OperationObject> createSimpleBinaryState(Boolean value) {
        Map<Boolean, OperationObject> state = new HashMap<>();
        state.put(value, createSimpleBeanOperation());
        return state;
    }

    private BeanOperationObject createSimpleBeanOperation() {
        return (BeanOperationObject) operationPersistenceService.create(creator.createRandomSimpleBeanOperation()).block();
    }

    private BeanPredicateObject createPredicate() {
        return (BeanPredicateObject) predicatePersistenceService.create(creator.createRandomPredicate()).block();
    }

}
