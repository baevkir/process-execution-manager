package com.pem.test.service;

import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
import com.pem.persistence.model.calculator.common.CalculatorEntity;
import com.pem.test.common.FongoConfig;
import com.pem.test.common.TestEntityCreator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FongoConfig.class)
public class CalculatorServiceTest {

    private TestEntityCreator creator = new TestEntityCreator();

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
        return calculatorPersistenceService.createCalculator(creator.createRandomBinaryCalculator());
    }
}
