package com.pem.test.loop;

import com.pem.config.AppConfig;
import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.loop.condition.DoWhileOperationImpl;
import com.pem.core.operation.loop.condition.WhileOperationImpl;
import com.pem.core.predicate.Predicate;
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
import java.math.BigInteger;
import java.util.Objects;

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
            public Mono<Boolean> apply(OperationContext context) {
                return Mono.just(context).map(operationContext -> new MathOperationContext(operationContext))
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
            public Mono<Boolean> apply(OperationContext context) {
                return Mono.just(false);
            }
        };
    }

    @Test
    public void testWhileOperation() {
        WhileOperationImpl operation = new WhileOperationImpl();
        operation.setOperation(multiplyOperation);
        operation.setPredicate(predicate);

        Mono<MathOperationContext> result = Mono.just(new MathOperationContext())
                .doOnSuccess(mathContext -> mathContext.setFirstParam(BigDecimal.valueOf(2)))
                .doOnSuccess(mathContext -> mathContext.setResult(BigDecimal.valueOf(2)))
                .doOnSuccess(mathContext -> mathContext.open())
                .flatMap(context -> operation.execute(context))
                .doOnNext(operationContext -> operationContext.close())
                .map(operationContext -> new MathOperationContext(operationContext)).single();

        StepVerifier.create(result)
                .expectNextMatches(operationContext -> Objects.equals(operationContext.getResult(), BigDecimal.valueOf(128)))
                .verifyComplete();
    }

    @Test
    public void testDoWhileOperation() {
        DoWhileOperationImpl doWhileOperation = new DoWhileOperationImpl();
        doWhileOperation.setOperation(multiplyOperation);
        doWhileOperation.setPredicate(predicateFalse);

        Mono<MathOperationContext> result = Mono.just(new MathOperationContext())
                .doOnSuccess(mathContext -> mathContext.setFirstParam(BigDecimal.valueOf(2)))
                .doOnSuccess(mathContext -> mathContext.setResult(BigDecimal.valueOf(2)))
                .doOnSuccess(mathContext -> mathContext.open())
                .flatMap(context -> doWhileOperation.execute(context))
                .doOnNext(operationContext -> operationContext.close())
                .map(operationContext -> new MathOperationContext(operationContext)).single();

        StepVerifier.create(result)
                .expectNextMatches(operationContext -> Objects.equals(operationContext.getResult(), BigDecimal.valueOf(4)))
                .verifyComplete();
    }
}
