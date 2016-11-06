package com.pem.persistence.service;

import com.pem.config.FongoConfig;
import com.pem.persistence.model.calculator.BinaryCalculator;
import com.pem.persistence.model.calculator.common.BeanCalculatorEntity;
import com.pem.persistence.model.calculator.common.CalculatorEntity;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.persistence.service.calculator.CalculatorPersistenceService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FongoConfig.class)
public class CalculatorServiceTest {

    private Random random = new Random();

    @Autowired
    private CalculatorPersistenceService calculatorPersistenceService;

    @Test
    public void testSaveToDBCalculator() {
        CalculatorEntity calculatorEntity = createBinaryCalculator();
        CalculatorEntity queryCalculator = calculatorPersistenceService.getCalculator(calculatorEntity.getId());
        Assert.assertEquals(calculatorEntity.getClass(), queryCalculator.getClass());
    }

    @Test
    public void testDeleteOperation() {
        CalculatorEntity calculatorEntity = createBinaryCalculator();
        BigInteger id = calculatorEntity.getId();

        calculatorPersistenceService.deleteCalculator(id);

        CalculatorEntity testOperation = calculatorPersistenceService.getCalculator(id);
        Assert.assertNull(testOperation);
    }

    private CalculatorEntity createBinaryCalculator() {
        BeanCalculatorEntity calculatorEntity = new BinaryCalculator();
        calculatorEntity.setName("Test Calculator " + random.nextLong());
        calculatorEntity.setDescription("Test Calculator description " + random.nextLong());

        BeanEntity bean = new BeanEntity();
        bean.setName("Test Calculation");
        bean.setBeanName("testCalculation");
        calculatorEntity.setBean(bean);

        return calculatorPersistenceService.createCalculator(calculatorEntity);
    }
}
