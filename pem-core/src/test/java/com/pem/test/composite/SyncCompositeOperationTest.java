package com.pem.test.composite;

import com.pem.config.AppConfig;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.composite.SyncCompositeOperationImp;
import com.pem.logic.*;
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
public class SyncCompositeOperationTest {

    private Operation sumOperation;
    private Operation putToFirstOperation;
    private Operation putToSecondOperation;
    private Operation subtractOperation;

    @Before
    public void setUp() {
        sumOperation = new SumOperation();
        putToFirstOperation = new PutResultToFirstParam();
        putToSecondOperation = new PutResultToSecondParam();
        subtractOperation = new SubtractOperation();
    }

    @Test
    public void testSameOperations() {
        SyncCompositeOperationImp operation = new SyncCompositeOperationImp();
         operation.addOperation(sumOperation); // 5 + 7 = 12
         operation.addOperation(putToFirstOperation);
         operation.addOperation(sumOperation); // 12 + 7 = 19
         operation.addOperation(putToSecondOperation);
         operation.addOperation(sumOperation); // 12 + 19 = 31
         operation.addOperation(putToFirstOperation);
         operation.addOperation(sumOperation); // 31 + 19 = 50
         operation.addOperation(putToSecondOperation);
         operation.addOperation(sumOperation); // 31 + 50 = 81

        Mono<MathOperationContext> result = Mono.just(new MathOperationContext())
                .doOnSuccess(operationContext -> operationContext.open())
                .doOnSuccess(operationContext -> operationContext.setFirstParam(BigDecimal.valueOf(5)))
                .doOnSuccess(operationContext -> operationContext.setSecondParam(BigDecimal.valueOf(7)))
                .flatMap(operationContext -> operation.execute(operationContext))
                .doOnNext(operationContext -> operationContext.close())
                .map(operationContext -> new MathOperationContext(operationContext))
                .single();

        StepVerifier.create(result)
                .expectNextMatches(operationContext -> Objects.equals(operationContext.getResult(), BigDecimal.valueOf(81)))
                .verifyComplete();
    }

}
