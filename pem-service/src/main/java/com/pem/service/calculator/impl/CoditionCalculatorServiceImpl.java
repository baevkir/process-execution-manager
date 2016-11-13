package com.pem.service.calculator.impl;

import com.pem.common.provider.calculator.ConditionCalculatorProvider;
import com.pem.common.provider.calculator.impl.RegisterGlobalCalculator;
import com.pem.conditioncalculator.ConditionCalculator;
import com.pem.persistence.model.calculator.common.CalculatorEntity;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.persistence.service.calculator.CalculatorPersistenceService;
import com.pem.service.calculator.ConditionCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoditionCalculatorServiceImpl implements ConditionCalculatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoditionCalculatorServiceImpl.class);

    private ConditionCalculatorProvider calculatorProvider;

    private CalculatorPersistenceService persistenceService;

    @Override
    public CalculatorEntity createConditionCalculator(CalculatorEntity calculatorEntity) {
        LOGGER.debug("Create new ConditionCalculator: {}.", calculatorEntity);
        return persistenceService.createCalculator(calculatorEntity);
    }

    @Override
    public void updateConditionCalculator(CalculatorEntity calculatorEntity) {
        LOGGER.debug("Update ConditionCalculator: {}.", calculatorEntity);
        persistenceService.updateCalculator(calculatorEntity);
    }

    @Override
    public void deleteConditionCalculator(BigInteger id) {
        LOGGER.debug("Delete ConditionCalculator by id: {}.", id);
        persistenceService.deleteCalculator(id);
    }

    @Override
    public CalculatorEntity getConditionCalculator(BigInteger id) {
        LOGGER.debug("Get ConditionCalculator by id: {}.", id);
        return persistenceService.getCalculator(id);
    }

    @Override
    public List<CalculatorEntity> getAllConditionCalculators() {
        LOGGER.debug("Get All ConditionCalculators.");
        return persistenceService.getAllCalculators();
    }

    @Override
    public <C extends ConditionCalculator> List<BeanEntity> getConditionCalculatorBeanEntitiesForClass(Class<C> tClass) {
        LOGGER.debug("Get All ConditionCalculatorBeanEntities for class {}.", tClass);
        List<BeanEntity> calculators = new ArrayList<>();

        Map<String, C> beans = calculatorProvider.getAllGlobalCalculators(tClass);
        for (Map.Entry<String, C> entry : beans.entrySet()) {
            BeanEntity calculator = new BeanEntity();
            String beanName = entry.getKey();
            LOGGER.trace("Add bean with name {}", beanName);
            calculator.setBeanName(beanName);

            Class clazz = AopProxyUtils.ultimateTargetClass(entry.getValue());
            RegisterGlobalCalculator annotation = (RegisterGlobalCalculator) clazz.getAnnotation(RegisterGlobalCalculator.class);
            String name = annotation.value();
            LOGGER.trace("Presentation name for bean {}", name);
            calculator.setName(name);

            calculators.add(calculator);
        }

        return calculators;
    }

    public void setCalculatorProvider(ConditionCalculatorProvider calculatorProvider) {
        this.calculatorProvider = calculatorProvider;
    }

    public void setPersistenceService(CalculatorPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }
}
