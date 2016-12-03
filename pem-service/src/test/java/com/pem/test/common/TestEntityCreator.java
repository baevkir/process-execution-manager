package com.pem.test.common;

import com.pem.model.calculator.bean.BinaryBeanConditionCalculatorDTO;
import com.pem.model.calculator.bean.IntegerBeanConditionCalculatorDTO;
import com.pem.model.common.bean.BeanObject;
import com.pem.model.operation.basic.BeanOperationDTO;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.operation.composite.SyncCompositeOperationDTO;
import com.pem.model.operation.condition.BinaryConditionOperationDTO;
import com.pem.model.operation.condition.IntegerConditionOperationDTO;

import java.util.*;

public class TestEntityCreator {

    private Random random = new Random();

    public BinaryBeanConditionCalculatorDTO createBinaryCalculator() {
        return createBinaryCalculator("testBinaryConditionCalculator");
    }

    public BinaryBeanConditionCalculatorDTO createRandomBinaryCalculator() {
        return createBinaryCalculator(String.valueOf(random.nextInt()));
    }

    public IntegerBeanConditionCalculatorDTO createIntegerCalculator() {
        return createIntegerCalculator("testIntegerConditionCalculator");
    }

    public IntegerBeanConditionCalculatorDTO createRandomIntegerCalculator() {
        return createIntegerCalculator(String.valueOf(random.nextInt()));
    }
    public BeanOperationDTO createSimpleBeanOperation() {
        return createSimpleBeanOperation("sumOperation");
    }

    public BeanOperationDTO createRandomSimpleBeanOperation() {
        return createSimpleBeanOperation(String.valueOf(random.nextInt()));
    }
    public BinaryConditionOperationDTO createBinaryConditionOperationEntity(){
        BinaryConditionOperationDTO operationEntity = new BinaryConditionOperationDTO();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        Map<Boolean, OperationDTO> states = new HashMap<>();
        states.putAll(createSimpleBinaryState(true));
        states.putAll(createSimpleBinaryState(false));
        operationEntity.setStates(states);

        operationEntity.setCalculatorEntity(createBinaryCalculator());
        return operationEntity;
    }

    public IntegerConditionOperationDTO createIntegerConditionOperationEntity(){
        IntegerConditionOperationDTO operationEntity = new IntegerConditionOperationDTO();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        Map<Integer, OperationDTO> states = new HashMap<>();
        states.putAll(createSimpleIntegerState(0));
        states.putAll(createSimpleIntegerState(1));
        operationEntity.setStates(states);

        operationEntity.setCalculatorEntity(createIntegerCalculator());
        return operationEntity;
    }

    public SyncCompositeOperationDTO createSyncCompositeOperationEntity() {
        SyncCompositeOperationDTO operationEntity = new SyncCompositeOperationDTO();
        operationEntity.setName("Test composite operation " + random.nextLong());
        List<OperationDTO> operationEntities = new ArrayList<>();
        operationEntities.add(createSimpleBeanOperation());
        operationEntities.add(createSimpleBeanOperation());
        operationEntities.add(createSimpleBeanOperation());
        operationEntities.add(createSimpleBeanOperation());

        operationEntity.setOperations(operationEntities);
        return operationEntity;
    }

    private Map<Boolean, OperationDTO> createSimpleBinaryState(Boolean value) {
        Map<Boolean, OperationDTO> state = new HashMap<>();
        state.put(value, createSimpleBeanOperation());
        return state;
    }

    private Map<Integer, OperationDTO> createSimpleIntegerState(Integer value) {
        Map<Integer, OperationDTO> state = new HashMap<>();
        state.put(value, createSimpleBeanOperation());
        return state;
    }

    private BeanOperationDTO createSimpleBeanOperation(String beanName) {
        BeanOperationDTO operationEntity = new BeanOperationDTO();
        operationEntity.setName("Test operation " + random.nextLong());
        operationEntity.setDescription("Test description " + random.nextLong());

        BeanObject bean = new BeanObject();
        bean.setName("Test Operation");
        bean.setBeanName(beanName);
        operationEntity.setBean(bean);

        return operationEntity;
    }

    private BinaryBeanConditionCalculatorDTO createBinaryCalculator(String beanName) {
        BinaryBeanConditionCalculatorDTO calculatorEntity = new BinaryBeanConditionCalculatorDTO();
        calculatorEntity.setName("Test Calculator " + random.nextLong());
        calculatorEntity.setDescription("Test Calculator description " + random.nextLong());

        BeanObject beanEntity = new BeanObject();
        beanEntity.setBeanName(beanName);
        calculatorEntity.setBean(beanEntity);

        return calculatorEntity;
    }

    private IntegerBeanConditionCalculatorDTO createIntegerCalculator(String beanName) {
        IntegerBeanConditionCalculatorDTO calculatorEntity = new IntegerBeanConditionCalculatorDTO();
        calculatorEntity.setName("Test Calculator " + random.nextLong());
        calculatorEntity.setDescription("Test Calculator description " + random.nextLong());

        BeanObject beanEntity = new BeanObject();
        beanEntity.setBeanName(beanName);
        calculatorEntity.setBean(beanEntity);

        return calculatorEntity;
    }
}
