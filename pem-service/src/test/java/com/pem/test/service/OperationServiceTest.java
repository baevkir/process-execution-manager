package com.pem.test.service;

import com.pem.integration.provider.PemServiceProviderImpl;
import com.pem.logic.service.operation.OperationService;
import com.pem.model.operation.common.OperationDTO;
import com.pem.test.common.TestEntityCreator;
import com.pem.test.common.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OperationServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PemServiceProviderImpl.class);

    @Autowired
    private OperationService operationService;

    private TestEntityCreator creator = new TestEntityCreator();

    @Test
    public void testCreateEvent() {
        Mono<OperationDTO> result = operationService.createOperation(creator.createSimpleBeanOperation());

        StepVerifier.create(result)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    public void testUpdateOperation() {
        Mono<Void> result = operationService.updateOperation(creator.createSimpleBeanOperation());

        StepVerifier.create(result)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    public void testDeleteOperation() {
        Mono<Void> result = operationService.deleteOperation(BigInteger.ONE);

        StepVerifier.create(result)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    public void testGetOperation() {
        Mono<OperationDTO> result = operationService.getOperation(BigInteger.ONE);

        StepVerifier.create(result)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    public void testGetAllOperation() {
        Flux<OperationDTO> result = operationService.getAllOperations();
        StepVerifier.create(result)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }
}
