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
        BinaryCalculator calculatorEntity = new BinaryCalculator();
        calculatorEntity.setName("Test Calculator " + random.nextLong());
        calculatorEntity.setDescription("Test Calculator description " + random.nextLong());

        BeanEntity beanEntity = new BeanEntity();
        beanEntity.setBeanName("testBinaryConditionCalculator");
        calculatorEntity.setBean(beanEntity);

        return calculatorEntity;
    }

    public IntegerCalculator createIntegerCalculator() {
        IntegerCalculator calculatorEntity = new IntegerCalculator();
        calculatorEntity.setName("Test Calculator " + random.nextLong());
        calculatorEntity.setDescription("Test Calculator description " + random.nextLong());

        BeanEntity beanEntity = new BeanEntity();
        beanEntity.setBeanName("testIntegerConditionCalculator");
        calculatorEntity.setBean(beanEntity);

        return calculatorEntity;
    }
    public BeanOperationEntity createSimpleBeanOperation() {
        BeanOperationEntity operationEntity = new BeanOperationEntity();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        BeanEntity bean = new BeanEntity();
        bean.setName("Sum Operation");
        bean.setBeanName("sumOperation");
        operationEntity.setBean(bean);

        return operationEntity;
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
}
