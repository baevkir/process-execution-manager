package com.pem.test.service;

import com.pem.logic.common.context.OperationContextFactory;
import com.pem.logic.common.utils.IdGenerator;
import com.pem.logic.service.context.OperationContextService;
import com.pem.logic.service.process.ExecutionProcessService;
import com.pem.model.proccess.ExecutionProcessObject;
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

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.pem.logic.MathOperationContext.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProcessServiceTest {
    @Autowired
    private ExecutionProcessService executionProcessService;

    @Autowired
    private OperationContextService operationContextService;

    private TestEntityCreator creator = new TestEntityCreator();

    @Test
    public void testCreateEvent() {
        Mono<ExecutionProcessObject> result = executionProcessService.createExecutionProcess(creator.createSimpleBeanOperation());
        StepVerifier.create(result)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    public void testUpdateEvent() {
        Mono<Void> result = executionProcessService.updateExecutionProcess(new ExecutionProcessObject());
        StepVerifier.create(result)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    public void testExecuteEvent() {
        OperationContextFactory contextFactory = operationContextService.getContextFactory()
                .setContextParam(FIRST_PARAM, BigDecimal.ONE)
                .setContextParam(SECOND_PARAM, BigDecimal.ONE);

        Mono<BigDecimal> resultMono = Mono.just(new ExecutionProcessObject())
                .doOnSuccess(processObject -> processObject.setId(IdGenerator.generateId()))
                .doOnSuccess(processObject -> processObject.setExecutionOperation(creator.createSimpleBeanOperation()))
                .flatMap(processObject -> executionProcessService.executeProcess(processObject, contextFactory))
                .single()
                .map(operationContext -> operationContext.getContextParam(RESULT_PARAM, BigDecimal.class));

        StepVerifier.create(resultMono)
                .expectNext(new BigDecimal(2))
                .expectComplete()
                .verify();
    }

    @Test
    public void testGetOneEvent() {
        Mono<ExecutionProcessObject> result = executionProcessService.getExecutionProcess(BigInteger.ONE);
        StepVerifier.create(result)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    public void testProcessListEvent() {
        Flux<ExecutionProcessObject> result = executionProcessService.getAllExecutionProcesses();
        StepVerifier.create(result)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }
}
