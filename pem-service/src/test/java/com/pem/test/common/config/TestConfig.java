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
import org.springframework.context.annotation.Scope;


@Configuration
@ImportResource("classpath:applicationContext.xml")
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
        return new Operation() {
            @Override
            public void execute(OperationContext context) {
                Assert.assertTrue(context.isOpen());
            }
        };
    }

    @Bean
    @Scope("prototype")
    public ConditionCalculator<Integer> compareFirstWithSecondCalculator() {
        return new CompareFirstWithSecondCalculator();
    }

    @Bean
    @Scope("prototype")
    public Operation globalOperation() {
        return new GlobalOperation();
    }
}
