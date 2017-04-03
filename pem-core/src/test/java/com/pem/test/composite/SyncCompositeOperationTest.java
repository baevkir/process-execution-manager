package com.pem.test.composite;

import com.pem.config.AppConfig;
import com.pem.core.context.OperationContext;
import com.pem.core.context.OperationContextImpl;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.composite.SyncCompositeOperationImp;
import com.pem.logic.*;
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
        Mono<OperationContext> context = Mono.just(new MathOperationContext(new OperationContextImpl()))
                .doOnSuccess(operationContext -> operationContext.open())
                .doOnSuccess(operationContext -> operationContext.setFirstParam(BigDecimal.valueOf(5)))
                .doOnSuccess(operationContext -> operationContext.setSecondParam(BigDecimal.valueOf(7)))
                .cast(OperationContext.class);

        Mono.just(new SyncCompositeOperationImp())
                .doOnSuccess(operation -> operation.addOperation(sumOperation)) // 5 + 7 = 12
                .doOnSuccess(operation -> operation.addOperation(putToFirstOperation))
                .doOnSuccess(operation -> operation.addOperation(sumOperation)) // 12 + 7 = 19
                .doOnSuccess(operation -> operation.addOperation(putToSecondOperation))
                .doOnSuccess(operation -> operation.addOperation(sumOperation)) // 12 + 19 = 31
                .doOnSuccess(operation -> operation.addOperation(putToFirstOperation))
                .doOnSuccess(operation -> operation.addOperation(sumOperation)) // 31 + 19 = 50
                .doOnSuccess(operation -> operation.addOperation(putToSecondOperation))
                .doOnSuccess(operation -> operation.addOperation(sumOperation)) // 31 + 50 = 81
                .flatMap(operation -> operation.execute(context))
                .map(operationContext -> new MathOperationContext(operationContext))
                .subscribe(operationContext -> {
                    operationContext.close();
                    Assert.assertEquals(operationContext.getResult(), BigDecimal.valueOf(81));
                });

    }

}
