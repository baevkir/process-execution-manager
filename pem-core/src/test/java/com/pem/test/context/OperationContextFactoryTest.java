package com.pem.test.context;

import com.pem.config.AppConfig;
import com.pem.core.context.OperationContext;
import com.pem.core.context.OperationContextFactory;
import com.pem.logic.MathOperationContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class OperationContextFactoryTest {

    @Test
    public void testDefaultContext() {
        Mono<OperationContext> context = OperationContextFactory.create().setId(BigInteger.ZERO).createContext();
        Assert.assertNotNull(context);

        StepVerifier.create(context)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    public void testCustomContext() {
        Mono<OperationContext> context = OperationContextFactory.create()
                .setId(BigInteger.ZERO)
                .setContextClass(MathOperationContext.class)
                .createContext();
        Assert.assertNotNull(context);

        StepVerifier.create(context)
                .expectNextCount(1)
                .assertNext(operationContext -> operationContext.getClass().equals(MathOperationContext.class))
                .expectComplete()
                .verify();
    }

    @Test
    public void testStringCustomContext() {
        Mono<OperationContext> context = OperationContextFactory.create()
                .setId(BigInteger.ZERO)
                .setContextClass("com.pem.logic.MathOperationContext")
                .createContext();
        Assert.assertNotNull(context);

        Assert.assertNotNull(context);

        StepVerifier.create(context)
                .expectNextCount(1)
                .assertNext(operationContext -> operationContext.getClass().equals(MathOperationContext.class))
                .expectComplete()
                .verify();
    }
}
