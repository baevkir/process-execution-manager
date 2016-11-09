package com.pem.test.common.config;

import com.pem.common.*;
import com.pem.conditioncalculator.ConditionCalculator;
import com.pem.context.OperationContext;
import com.pem.operation.basic.Operation;
import com.pem.test.common.GlobalOperation;
import org.junit.Assert;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@ImportResource("classpath:applicationContext.xml")
public class TestConfig {

    @Bean
    public Operation sumOperation() {
        return new SumOperation();
    }

    @Bean
    public Operation putResultToFirstParam() {
        return new PutResultToFirstParam();
    }

    @Bean
    public Operation putResultToSecondParam() {
        return new PutResultToSecondParam();
    }

    @Bean
    public Operation subtractOperation() {
        return new SubtractOperation();
    }

    @Bean
    public Operation checkOpenContextOperation() {
        return new Operation() {
            @Override
            public void execute(OperationContext context) {
                Assert.assertTrue(context.isOpen());
            }
        };
    }

    @Bean
    public ConditionCalculator<Integer> compareFirstWithSecondCalculator() {
        return new CompareFirstWithSecondCalculator();
    }

    @Bean
    public Operation globalOperation() {
        return new GlobalOperation();
    }
}
