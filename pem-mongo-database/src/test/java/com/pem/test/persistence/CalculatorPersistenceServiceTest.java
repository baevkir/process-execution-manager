package com.pem.test.persistence;

import com.pem.model.calculator.common.CalculatorDTO;
import com.pem.persistence.api.service.calculator.CalculatorPersistenceService;
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
public class CalculatorPersistenceServiceTest {

    private TestEntityCreator creator = new TestEntityCreator();

    @Autowired
    private CalculatorPersistenceService calculatorPersistenceService;

    @Test
    public void testSaveToDBCalculator() {
        CalculatorDTO calculatorEntity = createBinaryCalculator();
        CalculatorDTO queryCalculator = calculatorPersistenceService.getCalculator(calculatorEntity.getId());
        Assert.assertEquals(calculatorEntity.getClass(), queryCalculator.getClass());
    }

    @Test
    public void testDeleteOperation() {
        CalculatorDTO calculatorEntity = createBinaryCalculator();
        BigInteger id = calculatorEntity.getId();

        calculatorPersistenceService.deleteCalculator(id);

        CalculatorDTO testOperation = calculatorPersistenceService.getCalculator(id);
        Assert.assertNull(testOperation);
    }

    private CalculatorDTO createBinaryCalculator() {
        return calculatorPersistenceService.createCalculator(creator.createRandomBinaryCalculator());
    }
}
