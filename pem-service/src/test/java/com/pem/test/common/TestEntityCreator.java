package com.pem.test.common;

import com.pem.persistence.model.calculator.BinaryCalculator;
import com.pem.persistence.model.calculator.IntegerCalculator;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.persistence.model.operation.basic.BeanOperationEntity;
import com.pem.persistence.model.operation.common.OperationEntity;
import com.pem.persistence.model.operation.composite.SyncCompositeOperationEntity;
import com.pem.persistence.model.operation.condition.BinaryConditionOperationEntity;
import com.pem.persistence.model.operation.condition.IntegerConditionOperationEntity;
import com.pem.persistence.model.operation.condition.state.BooleanState;
import com.pem.persistence.model.operation.condition.state.IntegerState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestEntityCreator {

    private Random random = new Random();

    public BinaryCalculator createBinaryCalculator() {
        return createBinaryCalculator("testBinaryConditionCalculator");
    }

    public BinaryCalculator createRandomBinaryCalculator() {
        return createBinaryCalculator(String.valueOf(random.nextInt()));
    }

    public IntegerCalculator createIntegerCalculator() {
        return createIntegerCalculator("testIntegerConditionCalculator");
    }

    public IntegerCalculator createRandomIntegerCalculator() {
        return createIntegerCalculator(String.valueOf(random.nextInt()));
    }
    public BeanOperationEntity createSimpleBeanOperation() {
        return createSimpleBeanOperation("sumOperation");
    }

    public BeanOperationEntity createRandomSimpleBeanOperation() {
        return createSimpleBeanOperation(String.valueOf(random.nextInt()));
    }
    public BinaryConditionOperationEntity createBinaryConditionOperationEntity(){
        BinaryConditionOperationEntity operationEntity = new BinaryConditionOperationEntity();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        operationEntity.setStates(Arrays.asList(createSimpleBinaryState(true), createSimpleBinaryState(false)));

        operationEntity.setCalculatorEntity(createBinaryCalculator());
        return operationEntity;
    }

    public IntegerConditionOperationEntity createIntegerConditionOperationEntity(){
        IntegerConditionOperationEntity operationEntity = new IntegerConditionOperationEntity();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        operationEntity.setStates(Arrays.asList(createSimpleIntegerState(0),createSimpleIntegerState(1)));

        operationEntity.setCalculatorEntity(createIntegerCalculator());
        return operationEntity;
    }

    public SyncCompositeOperationEntity createSyncCompositeOperationEntity() {
        SyncCompositeOperationEntity operationEntity = new SyncCompositeOperationEntity();
        operationEntity.setName("Test composite operation " + random.nextLong());
        List<OperationEntity> operationEntities = new ArrayList<>();
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

    private BeanOperationEntity createSimpleBeanOperation(String beanName) {
        BeanOperationEntity operationEntity = new BeanOperationEntity();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        BeanEntity bean = new BeanEntity();
        bean.setName("Test Operation");
        bean.setBeanName(beanName);
        operationEntity.setBean(bean);

        return operationEntity;
    }

    private BinaryCalculator createBinaryCalculator(String beanName) {
        BinaryCalculator calculatorEntity = new BinaryCalculator();
        calculatorEntity.setName("Test Calculator " + random.nextLong());
        calculatorEntity.setDescription("Test Calculator description " + random.nextLong());

        BeanEntity beanEntity = new BeanEntity();
        beanEntity.setBeanName(beanName);
        calculatorEntity.setBean(beanEntity);

        return calculatorEntity;
    }

    private IntegerCalculator createIntegerCalculator(String beanName) {
        IntegerCalculator calculatorEntity = new IntegerCalculator();
        calculatorEntity.setName("Test Calculator " + random.nextLong());
        calculatorEntity.setDescription("Test Calculator description " + random.nextLong());

        BeanEntity beanEntity = new BeanEntity();
        beanEntity.setBeanName(beanName);
        calculatorEntity.setBean(beanEntity);

        return calculatorEntity;
    }
}
