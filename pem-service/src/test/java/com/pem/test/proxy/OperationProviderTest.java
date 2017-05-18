package com.pem.test.proxy;


import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.composite.SyncCompositeOperation;
import com.pem.core.operation.condition.trigger.TriggerOperation;
import com.pem.logic.CompareFirstWithSecondCalculator;
import com.pem.logic.MathOperationContext;
import com.pem.logic.bean.provider.BeanProvider;
import com.pem.test.common.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Objects;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OperationProviderTest {

    @Autowired
    private Operation sumOperation;

    @Autowired
    private Operation subtractOperation;

    @Autowired
    private Operation checkOpenContextOperation;

    @Autowired
    private BeanProvider provider;

    @Test
    public void testProxyOpenContextOnBaseOperation() {
        MathOperationContext context = new MathOperationContext();
        context.setFirstParam(BigDecimal.valueOf(12));
        context.setSecondParam(BigDecimal.valueOf(13));
        context.open();

        Mono<MathOperationContext> result = Mono.just(provider.createCommonInstance(SyncCompositeOperation.class))
                .doOnNext(compositeOperation -> compositeOperation.addOperation(checkOpenContextOperation))
                .doOnNext(compositeOperation -> compositeOperation.addOperation(sumOperation))
                .doOnNext(compositeOperation -> compositeOperation.addOperation(checkOpenContextOperation))
                .flatMap(compositeOperation -> compositeOperation.execute(context))
                .doOnNext(operationContext -> operationContext.close())
                .map(operationContext -> new MathOperationContext(operationContext))
                .single();

        StepVerifier.create(result)
                .expectNextMatches(operationContext -> Objects.equals(operationContext.getResult(), BigDecimal.valueOf(25)))
                .verifyComplete();
    }

    @Test
    public void testProxyOpenContextOnIntegerConditionOperation() {
        MathOperationContext context = new MathOperationContext();
        context.setFirstParam(BigDecimal.valueOf(12));
        context.setSecondParam(BigDecimal.valueOf(13));
        context.open();

        Mono<MathOperationContext> result = Mono.just(provider.createCommonInstance(TriggerOperation.class))
                .doOnNext(triggerOperation -> triggerOperation.addCondition(1, subtractOperation))
                .doOnNext(triggerOperation -> triggerOperation.addCondition(0, subtractOperation))
                .doOnNext(triggerOperation -> triggerOperation.addCondition(-1, sumOperation))
                .doOnNext(triggerOperation -> triggerOperation.setTrigger(new CompareFirstWithSecondCalculator()))
                .flatMap(triggerOperation -> triggerOperation.execute(context))
                .doOnNext(operationContext -> operationContext.close())
                .map(operationContext -> new MathOperationContext(operationContext))
                .single();

        StepVerifier.create(result)
                .expectNextMatches(operationContext -> Objects.equals(operationContext.getResult(), BigDecimal.valueOf(25)))
                .verifyComplete();
     }
}
