package com.pem.test.context;

import com.pem.core.context.OperationContext;
import com.pem.core.context.OperationContextImpl;
import com.pem.logic.MathOperationContext;
import com.pem.logic.service.context.OperationContextService;
import com.pem.test.common.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OperationContextFactoryTest {

    @Autowired
    private OperationContextService service;

    @Test
    public void testDefaultContext() {
        Mono<OperationContext> context = service.getContextFactory().setId(BigInteger.ZERO).createContext();
        Assert.assertNotNull(context);

        StepVerifier.create(context)
                .expectNextMatches(operationContext -> operationContext.getClass().equals(OperationContextImpl.class))
                .expectComplete()
                .verify();
    }

    @Test
    public void testCustomContext() {
        Mono<OperationContext> context = service.getContextFactory()
                .setId(BigInteger.ZERO)
                .setContextClass(MathOperationContext.class)
                .createContext();
        Assert.assertNotNull(context);

        StepVerifier.create(context)
                .expectNextMatches(operationContext -> operationContext.getClass().equals(MathOperationContext.class))
                .expectComplete()
                .verify();
    }

    @Test
    public void testStringCustomContext() {
        Mono<OperationContext> context = service.getContextFactory()
                .setId(BigInteger.ZERO)
                .setContextClass("com.pem.logic.MathOperationContext")
                .createContext();
        Assert.assertNotNull(context);

        Assert.assertNotNull(context);

        StepVerifier.create(context)
                .expectNextMatches(operationContext -> operationContext.getClass().equals(MathOperationContext.class))
                .expectComplete()
                .verify();
    }
}
