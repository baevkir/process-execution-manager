package com.pem.test.common.config;

import com.pem.common.*;
import com.pem.conditioncalculator.BinaryConditionCalculator;
import com.pem.conditioncalculator.IntegerConditionCalculator;
import com.pem.context.OperationContext;
import com.pem.operation.basic.AbstractOperation;
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
        return new AbstractOperation() {

            @Override
            public void execute(OperationContext context) {
                Assert.assertTrue(context.isOpen());
            }
        };
    }

    @Bean
    @Scope("prototype")
    public IntegerConditionCalculator compareFirstWithSecondCalculator() {
        return new CompareFirstWithSecondCalculator();
    }

    @Bean("testBinaryConditionCalculator")
    @Scope("prototype")
    public BinaryConditionCalculator testBinaryConditionCalculator() {
        return new BinaryConditionCalculator() {
            private String id;

            @Override
            public String getConditionCalculatorId() {
                return id;
            }

            @Override
            public void setConditionCalculatorId(String id) {
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
    public IntegerConditionCalculator testIntegerConditionCalculator() {
        return new IntegerConditionCalculator() {
            private String id;

            @Override
            public String getConditionCalculatorId() {
                return id;
            }

            @Override
            public void setConditionCalculatorId(String id) {
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
