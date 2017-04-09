package com.pem.test.common.config;

import com.pem.core.predicate.Predicate;
import com.pem.core.trigger.Trigger;
import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.AbstractOperation;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.*;
import com.pem.test.common.GlobalTestOperation;
import org.junit.Assert;
import org.springframework.context.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigInteger;


@Configuration
@ImportResource("classpath:pemApplicationContext.xml")
@Import(PersistenceMockConfig.class)
public class TestConfig {

    @Bean
    @Scope("prototype")
    public Operation sumOperation() {
        return new SumOperation();
    }

    @Bean
    @Scope("prototype")
    public Operation putResultToFirstParam() {
        return new PutResultToFirstParam();
    }

    @Bean
    @Scope("prototype")
    public Operation putResultToSecondParam() {
        return new PutResultToSecondParam();
    }

    @Bean
    @Scope("prototype")
    public Operation subtractOperation() {
        return new SubtractOperation();
    }

    @Bean
    @Scope("prototype")
    public Operation checkOpenContextOperation() {
        return new AbstractOperation() {

            @Override
            public Mono<OperationContext> execute(Mono<OperationContext> context) {
                return context.doOnSuccess(operationContext -> Assert.assertNotNull(operationContext))
                        .doOnSuccess(operationContext -> Assert.assertTrue(operationContext.isOpen()));
            }
        };
    }

    @Bean
    @Scope("prototype")
    public Trigger compareFirstWithSecondCalculator() {
        return new CompareFirstWithSecondCalculator();
    }

    @Bean("testBinaryConditionCalculator")
    @Scope("prototype")
    public Predicate testBinaryConditionCalculator() {
        return new Predicate() {
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
                return Mono.just(true);
            }
        };
    }

    @Bean("testIntegerConditionCalculator")
    @Scope("prototype")
    public Trigger testIntegerConditionCalculator() {
        return new Trigger() {
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
            public Mono<Integer> apply(Mono<OperationContext> context) {
                return Mono.just(0);
            }
        };
    }

    @Bean
    @Scope("prototype")
    public Operation globalOperation() {
        return new GlobalTestOperation();
    }
}
