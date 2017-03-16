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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FongoConfig.class)
public class CalculatorPersistenceServiceTest {

    private TestEntityCreator creator = new TestEntityCreator();

    @Autowired
    private CalculatorPersistenceService calculatorPersistenceService;

    @Test
    public void testSaveToDBCalculator() {
        Mono<CalculatorDTO> createResult = createBinaryCalculator();

        createResult.doOnNext(calculatorDTO -> {
            Mono<CalculatorDTO> getResult = calculatorPersistenceService.getCalculator(calculatorDTO.getId());

            getResult.doOnNext(queryCalculator -> Assert.assertEquals(calculatorDTO.getClass(), queryCalculator.getClass()));
        });

        StepVerifier.create(createResult)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    public void testDeleteOperation() {
        Mono<CalculatorDTO> createResult = createBinaryCalculator();


        createResult.doOnNext(calculatorDTO -> {
            BigInteger id = calculatorDTO.getId();
            calculatorPersistenceService.deleteCalculator(id).subscribe();

            Mono<CalculatorDTO> getResult = calculatorPersistenceService.getCalculator(id);

            StepVerifier.create(getResult)
                    .expectNextCount(0)
                    .expectComplete()
                    .verify();
        });


        StepVerifier.create(createResult)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    private Mono<CalculatorDTO> createBinaryCalculator() {
        return calculatorPersistenceService.createCalculator(creator.createRandomBinaryCalculator());
    }
}
