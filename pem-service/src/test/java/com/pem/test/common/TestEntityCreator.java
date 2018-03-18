package com.pem.test.common;

import com.pem.model.operation.bean.BeanOperationObject;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.predicate.bean.BeanPredicateObject;
import com.pem.model.trigger.bean.BeanTriggerObject;
import com.pem.core.common.applicationcontext.bean.BeanObject;
import com.pem.core.common.applicationcontext.bean.BeanObjectBuilder;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.model.operation.condition.PredicateOperationObject;
import com.pem.model.operation.condition.TriggerOperationObject;

import java.util.*;

public class TestEntityCreator {

    private Random random = new Random();

    public BeanPredicateObject createPredicate() {
        return createPredicate("testBinaryConditionCalculator");
    }

    public BeanPredicateObject createRandomPredicate() {
        return createPredicate(String.valueOf(random.nextInt()));
    }

    public BeanTriggerObject createTrigger() {
        return createTrigger("testIntegerConditionCalculator");
    }

    public BeanTriggerObject createRandomTrigger() {
        return createTrigger(String.valueOf(random.nextInt()));
    }
    public BeanOperationObject createSimpleBeanOperation() {
        return createSimpleBeanOperation("sumOperation");
    }

    public BeanOperationObject createRandomSimpleBeanOperation() {
        return createSimpleBeanOperation(String.valueOf(random.nextInt()));
    }
    public PredicateOperationObject createPredicateOperation(){
        PredicateOperationObject operationEntity = new PredicateOperationObject();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        Map<Boolean, OperationObject> states = new HashMap<>();
        states.putAll(createSimpleBinaryState(true));
        states.putAll(createSimpleBinaryState(false));
        operationEntity.setStates(states);

        operationEntity.setPredicate(createPredicate());
        return operationEntity;
    }

    public TriggerOperationObject createTriggerOperationEntity(){
        TriggerOperationObject operationEntity = new TriggerOperationObject();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        Map<Integer, OperationObject> states = new HashMap<>();
        states.putAll(createSimpleIntegerState(0));
        states.putAll(createSimpleIntegerState(1));
        operationEntity.setStates(states);

        operationEntity.setTrigger(createTrigger());
        return operationEntity;
    }

    public SyncCompositeOperationDTO createSyncCompositeOperationEntity() {
        SyncCompositeOperationDTO operationEntity = new SyncCompositeOperationDTO();
        operationEntity.setName("Test composite operation " + random.nextLong());
        List<OperationObject> operationEntities = new ArrayList<>();
        operationEntities.add(createSimpleBeanOperation());
        operationEntities.add(createSimpleBeanOperation());
        operationEntities.add(createSimpleBeanOperation());
        operationEntities.add(createSimpleBeanOperation());

        operationEntity.setOperations(operationEntities);
        return operationEntity;
    }

    private Map<Boolean, OperationObject> createSimpleBinaryState(Boolean value) {
        Map<Boolean, OperationObject> state = new HashMap<>();
        state.put(value, createSimpleBeanOperation());
        return state;
    }

    private Map<Integer, OperationObject> createSimpleIntegerState(Integer value) {
        Map<Integer, OperationObject> state = new HashMap<>();
        state.put(value, createSimpleBeanOperation());
        return state;
    }

    private BeanOperationObject createSimpleBeanOperation(String beanName) {
        BeanOperationObject operationEntity = new BeanOperationObject();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        BeanObject bean = BeanObjectBuilder.newInstance()
                .setBeanName(beanName)
                .setName("Test Operation")
                .build();

        operationEntity.setActive(true);
        operationEntity.setBean(bean);

        return operationEntity;
    }

    private BeanPredicateObject createPredicate(String beanName) {
        BeanPredicateObject calculatorEntity = new BeanPredicateObject();
        calculatorEntity.setName("Test Condition " + random.nextLong());
        calculatorEntity.setDescription("Test Condition description " + random.nextLong());

        BeanObject bean = BeanObjectBuilder.newInstance()
                .setBeanName(beanName)
                .setName("Test Operation")
                .build();
        calculatorEntity.setBean(bean);

        return calculatorEntity;
    }

    private BeanTriggerObject createTrigger(String beanName) {
        BeanTriggerObject calculatorEntity = new BeanTriggerObject();
        calculatorEntity.setName("Test Condition " + random.nextLong());
        calculatorEntity.setDescription("Test Condition description " + random.nextLong());

        BeanObject bean = BeanObjectBuilder.newInstance()
                .setBeanName(beanName)
                .setName("Test Operation")
                .build();
        calculatorEntity.setBean(bean);

        return calculatorEntity;
    }
}
