package com.pem.test.service;

import com.pem.core.context.OperationContextFactory;
import com.pem.logic.service.process.ExecutionProcessService;
import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.test.common.TestEntityCreator;
import com.pem.test.common.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProcessServiceTest {
    @Autowired
    private ExecutionProcessService executionProcessService;

    private TestEntityCreator creator = new TestEntityCreator();

    @Test
    public void testCreateEvent() {
        Mono<ExecutionProcessDTO> result = executionProcessService.createExecutionProcess(creator.createSimpleBeanOperation());
        StepVerifier.create(result)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    public void testUpdateEvent() {
        Mono<Void> result = executionProcessService.updateExecutionProcess(new ExecutionProcessDTO());
        StepVerifier.create(result)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    public void testExecuteEvent() {
        Mono<Void> result = executionProcessService.executeProcess(new ExecutionProcessDTO(), OperationContextFactory.create());
        StepVerifier.create(result)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    public void testGetOneEvent() {
        Mono<ExecutionProcessDTO> result = executionProcessService.getExecutionProcess(BigInteger.ONE);
        StepVerifier.create(result)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    public void testProcessListEvent() {
        Flux<ExecutionProcessDTO> result = executionProcessService.getAllExecutionProcesses();
        StepVerifier.create(result)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }
}
