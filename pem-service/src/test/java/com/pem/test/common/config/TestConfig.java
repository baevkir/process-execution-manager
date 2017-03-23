package com.pem.test.common.config;

import com.pem.core.calculator.BinaryCalculator;
import com.pem.core.calculator.IntegerCalculator;
import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.AbstractOperation;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.*;
import com.pem.test.common.GlobalOperation;
import org.junit.Assert;
import org.springframework.context.annotation.*;

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
            public void execute(OperationContext context) {
                Assert.assertTrue(context.isOpen());
            }
        };
    }

    @Bean
    @Scope("prototype")
    public IntegerCalculator compareFirstWithSecondCalculator() {
        return new CompareFirstWithSecondCalculator();
    }

    @Bean("testBinaryConditionCalculator")
    @Scope("prototype")
    public BinaryCalculator testBinaryConditionCalculator() {
        return new BinaryCalculator() {
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
            public Boolean calculate(OperationContext context) {
                return true;
            }
        };
    }

    @Bean("testIntegerConditionCalculator")
    @Scope("prototype")
    public IntegerCalculator testIntegerConditionCalculator() {
        return new IntegerCalculator() {
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
            public Integer calculate(OperationContext context) {
                return 0;
            }
        };
    }

    @Bean
    @Scope("prototype")
    public Operation globalOperation() {
        return new GlobalOperation();
    }
}
