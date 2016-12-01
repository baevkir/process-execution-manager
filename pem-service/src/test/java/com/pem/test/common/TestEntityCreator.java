package com.pem.test.common;

import com.pem.persistence.api.model.calculator.bean.BinaryBeanConditionCalculator;
import com.pem.persistence.api.model.calculator.bean.IntegerBeanConditionCalculator;
import com.pem.persistence.api.model.common.bean.BeanObject;
import com.pem.persistence.api.model.operation.basic.BeanOperationObject;
import com.pem.persistence.api.model.operation.common.OperationObject;
import com.pem.persistence.api.model.operation.composite.SyncCompositeOperationObject;
import com.pem.persistence.api.model.operation.condition.BinaryConditionOperationObject;
import com.pem.persistence.api.model.operation.condition.IntegerConditionOperationObject;
import com.pem.persistence.api.model.operation.condition.state.BooleanState;
import com.pem.persistence.api.model.operation.condition.state.IntegerState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestEntityCreator {

    private Random random = new Random();

    public BinaryBeanConditionCalculator createBinaryCalculator() {
        return createBinaryCalculator("testBinaryConditionCalculator");
    }

    public BinaryBeanConditionCalculator createRandomBinaryCalculator() {
        return createBinaryCalculator(String.valueOf(random.nextInt()));
    }

    public IntegerBeanConditionCalculator createIntegerCalculator() {
        return createIntegerCalculator("testIntegerConditionCalculator");
    }

    public IntegerBeanConditionCalculator createRandomIntegerCalculator() {
        return createIntegerCalculator(String.valueOf(random.nextInt()));
    }
    public BeanOperationObject createSimpleBeanOperation() {
        return createSimpleBeanOperation("sumOperation");
    }

    public BeanOperationObject createRandomSimpleBeanOperation() {
        return createSimpleBeanOperation(String.valueOf(random.nextInt()));
    }
    public BinaryConditionOperationObject createBinaryConditionOperationEntity(){
        BinaryConditionOperationObject operationEntity = new BinaryConditionOperationObject();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        operationEntity.setStates(Arrays.asList(createSimpleBinaryState(true), createSimpleBinaryState(false)));

        operationEntity.setCalculatorEntity(createBinaryCalculator());
        return operationEntity;
    }

    public IntegerConditionOperationObject createIntegerConditionOperationEntity(){
        IntegerConditionOperationObject operationEntity = new IntegerConditionOperationObject();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        operationEntity.setStates(Arrays.asList(createSimpleIntegerState(0),createSimpleIntegerState(1)));

        operationEntity.setCalculatorEntity(createIntegerCalculator());
        return operationEntity;
    }

    public SyncCompositeOperationObject createSyncCompositeOperationEntity() {
        SyncCompositeOperationObject operationEntity = new SyncCompositeOperationObject();
        operationEntity.setName("Test composite operation " + random.nextLong());
        List<OperationObject> operationEntities = new ArrayList<>();
        operationEntities.add(createSimpleBeanOperation());
        operationEntities.add(createSimpleBeanOperation());
        operationEntities.add(createSimpleBeanOperation());
        operationEntities.add(createSimpleBeanOperation());

        operationEntity.setOperationEntities(operationEntities);
        return operationEntity;
    }

    private BooleanState createSimpleBinaryState(Boolean value) {
        BooleanState state = new BooleanState();
        state.setConditionValue(value);
        state.setOperationEntity(createSimpleBeanOperation());

        return state;
    }

    private IntegerState createSimpleIntegerState(Integer value) {
        IntegerState state = new IntegerState();
        state.setConditionValue(value);
        state.setOperationEntity(createSimpleBeanOperation());

        return state;
    }

    private BeanOperationObject createSimpleBeanOperation(String beanName) {
        BeanOperationObject operationEntity = new BeanOperationObject();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        BeanObject bean = new BeanObject();
        bean.setName("Test Operation");
        bean.setBeanName(beanName);
        operationEntity.setBean(bean);

        return operationEntity;
    }

    private BinaryBeanConditionCalculator createBinaryCalculator(String beanName) {
        BinaryBeanConditionCalculator calculatorEntity = new BinaryBeanConditionCalculator();
        calculatorEntity.setName("Test Calculator " + random.nextLong());
        calculatorEntity.setDescription("Test Calculator description " + random.nextLong());

        BeanObject beanEntity = new BeanObject();
        beanEntity.setBeanName(beanName);
        calculatorEntity.setBean(beanEntity);

        return calculatorEntity;
    }

    private IntegerBeanConditionCalculator createIntegerCalculator(String beanName) {
        IntegerBeanConditionCalculator calculatorEntity = new IntegerBeanConditionCalculator();
        calculatorEntity.setName("Test Calculator " + random.nextLong());
        calculatorEntity.setDescription("Test Calculator description " + random.nextLong());

        BeanObject beanEntity = new BeanObject();
        beanEntity.setBeanName(beanName);
        calculatorEntity.setBean(beanEntity);

        return calculatorEntity;
    }
}
