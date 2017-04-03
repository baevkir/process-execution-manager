package com.pem.test.loop;

import com.pem.config.AppConfig;
import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.loop.counter.CounterLoopOperationImpl;
import com.pem.logic.MathOperationContext;
import com.pem.logic.MultiplyOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

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
        Mono<OperationContext> context = Mono.just(new MathOperationContext())
                .doOnNext(operationContext -> operationContext.setFirstParam(BigDecimal.valueOf(2)))
                .doOnNext(operationContext -> operationContext.setResult(BigDecimal.valueOf(2)))
                .doOnNext(operationContext -> operationContext.open())
                .cast(OperationContext.class);

        Mono.just(new CounterLoopOperationImpl())
                .doOnNext(counterLoopOperation -> counterLoopOperation.setCount(4))
                .doOnNext(counterLoopOperation -> counterLoopOperation.setOperation(multiplyOperation))
                .flatMap(counterLoopOperation -> counterLoopOperation.execute(context))
                .doOnNext(operationContext -> operationContext.close())
                .map(operationContext -> new MathOperationContext(operationContext))
                .subscribe(operationContext -> Assert.assertEquals(operationContext.getResult(), BigDecimal.valueOf(32)));
    }
}
