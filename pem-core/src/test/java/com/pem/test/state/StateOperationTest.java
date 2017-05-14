package com.pem.test.state;

import com.pem.config.AppConfig;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.composite.SyncCompositeOperationImp;
import com.pem.core.operation.condition.trigger.TriggerOperation;
import com.pem.core.operation.condition.trigger.TriggerOperationImpl;
import com.pem.logic.CompareFirstWithSecondCalculator;
import com.pem.logic.MathOperationContext;
import com.pem.logic.SubtractOperation;
import com.pem.logic.SwitchParamsOperation;
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
public class StateOperationTest {

    private Operation switchOperation;
    private Operation subtractOperation;
    private TriggerOperation triggerOperation;

    @Before
    public void setUp() {
        switchOperation = new SwitchParamsOperation();
        subtractOperation = new SubtractOperation();

        triggerOperation = new TriggerOperationImpl();
        triggerOperation.addCondition(1, subtractOperation);
        triggerOperation.addCondition(0, subtractOperation);
        triggerOperation.addCondition(-1, getSwitchAndSubtractOperation());
        triggerOperation.setTrigger(new CompareFirstWithSecondCalculator());
    }

    @Test
    public void testFirstGreaterOperations() {
        Mono<MathOperationContext> result = Mono.just(new MathOperationContext())
                .doOnSuccess(operationContext -> operationContext.open())
                .doOnSuccess(operationContext -> operationContext.setFirstParam(BigDecimal.valueOf(5)))
                .doOnSuccess(operationContext -> operationContext.setSecondParam(BigDecimal.valueOf(3)))
                .flatMap(operationContext -> triggerOperation.execute(operationContext))
                .doOnNext(operationContext -> operationContext.close())
                .map(operationContext -> new MathOperationContext(operationContext))
                .single();

        StepVerifier.create(result)
                .expectNextMatches(operationContext -> Objects.equals(operationContext.getResult(), BigDecimal.valueOf(2)))
                .verifyComplete();
    }

    @Test
    public void testFirstEqualOperations() {
        Mono<MathOperationContext> result = Mono.just(new MathOperationContext()).log()
                .doOnSuccess(operationContext -> operationContext.open())
                .doOnSuccess(operationContext -> operationContext.setFirstParam(BigDecimal.valueOf(15)))
                .doOnSuccess(operationContext -> operationContext.setSecondParam(BigDecimal.valueOf(15)))
                .flatMap(operationContext -> triggerOperation.execute(operationContext))
                .doOnNext(operationContext -> operationContext.close())
                .map(operationContext -> new MathOperationContext(operationContext))
                .single();

        StepVerifier.create(result)
                .expectNextMatches(operationContext -> Objects.equals(operationContext.getResult(),  BigDecimal.ZERO))
                .verifyComplete();
    }

    @Test
    public void testFirstLessOperations() {
        Mono<MathOperationContext> result = Mono.just(new MathOperationContext())
                .doOnSuccess(operationContext -> operationContext.open())
                .doOnSuccess(operationContext -> operationContext.setFirstParam(BigDecimal.valueOf(12)))
                .doOnSuccess(operationContext -> operationContext.setSecondParam(BigDecimal.valueOf(17)))
                .flatMap(operationContext -> triggerOperation.execute(operationContext))
                .doOnNext(operationContext -> operationContext.close())
                .map(operationContext -> new MathOperationContext(operationContext))
                .single();

        StepVerifier.create(result)
                .expectNextMatches(operationContext -> Objects.equals(operationContext.getResult(),  BigDecimal.valueOf(5)))
                .verifyComplete();
    }

    private Operation getSwitchAndSubtractOperation() {
        SyncCompositeOperationImp operation = new SyncCompositeOperationImp();
        operation.addOperation(switchOperation);
        operation.addOperation(subtractOperation);
        return operation;
    }
}
