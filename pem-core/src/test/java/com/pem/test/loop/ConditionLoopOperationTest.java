package com.pem.test.loop;

import com.pem.config.AppConfig;
import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.loop.condition.DoWhileOperationImpl;
import com.pem.core.operation.loop.condition.WhileOperationImpl;
import com.pem.core.predicate.Predicate;
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
import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class ConditionLoopOperationTest {

    private Operation multiplyOperation;
    private Predicate predicate;
    private Predicate predicateFalse;

    @Before
    public void setUp() {

        multiplyOperation = new MultiplyOperation();

        predicate = new Predicate() {

            private BigInteger id;

            @Override
            public BigInteger getId() {
                return id;
            }

            @Override
            public void setId(BigInteger id) {
                this.id = id;
            }

            @Override
            public Mono<Boolean> apply(Mono<OperationContext> context) {
                return context.map(operationContext -> new MathOperationContext(operationContext))
                        .map(contextWrapper -> contextWrapper.getResult().compareTo(BigDecimal.valueOf(100)) == -1);
            }
        };

        predicateFalse = new Predicate() {
            private BigInteger id;

            @Override
            public BigInteger getId() {
                return id;
            }

            @Override
            public void setId(BigInteger id) {
                this.id = id;
            }

            @Override
            public Mono<Boolean> apply(Mono<OperationContext> context) {
                return Mono.just(false);
            }
        };
    }

    @Test
    public void testWhileOperation() {
        Mono<OperationContext> context = Mono.just(new MathOperationContext())
                .doOnSuccess(operationContext -> operationContext.setFirstParam(BigDecimal.valueOf(2)))
                .doOnSuccess(operationContext -> operationContext.setResult(BigDecimal.valueOf(2)))
                .doOnSuccess(operationContext -> operationContext.open())
                .cast(OperationContext.class);

        Mono.just(new WhileOperationImpl())
                .doOnSuccess(whileOperation -> whileOperation.setOperation(multiplyOperation))
                .doOnSuccess(whileOperation -> whileOperation.setPredicate(predicate))
                .flatMap(whileOperation -> whileOperation.execute(context))
                .map(operationContext -> new MathOperationContext(operationContext)).single()
                .doOnSuccess(operationContext -> operationContext.close())
                .subscribe(operationContext -> Assert.assertEquals(operationContext.getResult(), BigDecimal.valueOf(128)));
    }

    @Test
    public void testDoWhileOperation() {
        Mono<OperationContext> context = Mono.just(new MathOperationContext())
                .doOnSuccess(mathContext -> mathContext.setFirstParam(BigDecimal.valueOf(2)))
                .doOnSuccess(mathContext -> mathContext.setResult(BigDecimal.valueOf(2)))
                .doOnSuccess(mathContext -> mathContext.open())
                .cast(OperationContext.class);

        Mono.just(new DoWhileOperationImpl())
                .doOnSuccess(doWhileOperation -> doWhileOperation.setOperation(multiplyOperation))
                .doOnSuccess(doWhileOperation -> doWhileOperation.setPredicate(predicateFalse))
                .flatMap(doWhileOperation -> doWhileOperation.execute(context)).single()
                .map(operationContext -> new MathOperationContext(operationContext))
                .doOnSuccess(operationContext -> operationContext.close())
                .subscribe(operationContext -> Assert.assertEquals(operationContext.getResult(), BigDecimal.valueOf(4)));

    }
}
