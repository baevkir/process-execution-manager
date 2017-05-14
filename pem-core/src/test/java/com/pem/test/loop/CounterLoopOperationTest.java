package com.pem.test.loop;

import com.pem.config.AppConfig;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.loop.counter.CounterLoopOperationImpl;
import com.pem.logic.MathOperationContext;
import com.pem.logic.MultiplyOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Objects;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class CounterLoopOperationTest {

    private Operation multiplyOperation;

    @Before
    public void setUp() {
        multiplyOperation = new MultiplyOperation();
    }

    @Test
    public void testCounterLoopOperation() {
        CounterLoopOperationImpl operation = new CounterLoopOperationImpl();
        operation.setCount(4);
        operation.setOperation(multiplyOperation);

        Mono<MathOperationContext> result = Mono.just(new MathOperationContext())
                .doOnNext(operationContext -> operationContext.open())
                .doOnNext(operationContext -> operationContext.setFirstParam(BigDecimal.valueOf(2)))
                .doOnNext(operationContext -> operationContext.setResult(BigDecimal.valueOf(2)))
                .flatMap(context -> operation.execute(context))
                .doOnNext(operationContext -> operationContext.close())
                .map(operationContext -> new MathOperationContext(operationContext)).single();

        StepVerifier.create(result)
                .expectNextMatches(operationContext -> Objects.equals(operationContext.getResult(), BigDecimal.valueOf(32)))
                .verifyComplete();
    }
}
