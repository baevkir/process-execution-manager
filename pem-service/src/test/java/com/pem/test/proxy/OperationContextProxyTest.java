package com.pem.test.proxy;


import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.composite.SyncCompositeOperation;
import com.pem.core.operation.condition.trigger.TriggerOperation;
import com.pem.logic.CompareFirstWithSecondCalculator;
import com.pem.logic.MathOperationContext;
import com.pem.logic.bean.provider.BeanProvider;
import com.pem.test.common.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OperationContextProxyTest {

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
        Mono<OperationContext> context = Mono.just(new MathOperationContext())
                .doOnNext(operationContext -> operationContext.setFirstParam(BigDecimal.valueOf(12)))
                .doOnNext(operationContext -> operationContext.setSecondParam(BigDecimal.valueOf(13)))
                .cast(OperationContext.class);

        Mono.just(provider.createCommonInstance(SyncCompositeOperation.class))
                .doOnNext(compositeOperation -> compositeOperation.addOperation(checkOpenContextOperation))
                .doOnNext(compositeOperation -> compositeOperation.addOperation(sumOperation))
                .doOnNext(compositeOperation -> compositeOperation.addOperation(checkOpenContextOperation))
                .flatMap(compositeOperation -> compositeOperation.execute(context))
                .map(operationContext -> new MathOperationContext(operationContext))
                .subscribe(operationContext -> Assert.assertEquals(operationContext.getResult(), BigDecimal.valueOf(25)));
    }

    @Test
    public void testProxyOpenContextOnIntegerConditionOperation() {
        Mono<OperationContext> context = Mono.just(new MathOperationContext())
                .doOnNext(operationContext -> operationContext.setFirstParam(BigDecimal.valueOf(12)))
                .doOnNext(operationContext -> operationContext.setSecondParam(BigDecimal.valueOf(13)))
                .cast(OperationContext.class);

        Mono.just(provider.createCommonInstance(TriggerOperation.class))
                .doOnNext(triggerOperation -> triggerOperation.addCondition(1, subtractOperation))
                .doOnNext(triggerOperation -> triggerOperation.addCondition(0, subtractOperation))
                .doOnNext(triggerOperation -> triggerOperation.addCondition(-1, sumOperation))
                .doOnNext(triggerOperation -> triggerOperation.setTrigger(new CompareFirstWithSecondCalculator()))
                .flatMap(triggerOperation -> triggerOperation.execute(context))
                .map(operationContext -> new MathOperationContext(operationContext))
                .subscribe(operationContext -> Assert.assertEquals(operationContext.getResult(), BigDecimal.valueOf(25)));
    }
}
