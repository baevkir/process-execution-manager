package com.pem.test.loop;

import com.pem.logic.MathOperationContext;
import com.pem.logic.MultiplyOperation;
import com.pem.config.AppConfig;
import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.Operation;
import com.pem.core.conditioncalculator.BinaryConditionCalculator;
import com.pem.core.operation.loop.condition.ConditionLoopOperation;
import com.pem.core.operation.loop.condition.DoWhileOperationImpl;
import com.pem.core.operation.loop.condition.WhileOperationImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class ConditionLoopOperationTest {

    private Operation multiplyOperation;
    private BinaryConditionCalculator calculator;
    private BinaryConditionCalculator calculatorFalse;

    @Before
    public void setUp() {

        multiplyOperation = new MultiplyOperation();

        calculator = new BinaryConditionCalculator () {

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
                MathOperationContext contextWrapper = new MathOperationContext(context);
                return contextWrapper.getResult().compareTo(BigDecimal.valueOf(100)) == -1;
            }
        };

        calculatorFalse = new BinaryConditionCalculator() {
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
               return false;
            }
        };
    }
    @Test
    public void testWhileOperation() {
        ConditionLoopOperation loopOperation = new WhileOperationImpl();
        loopOperation.setOperation(multiplyOperation);
        loopOperation.setCalculator(calculator);

        MathOperationContext context = new MathOperationContext();
        context.setFirstParam(BigDecimal.valueOf(2));
        context.setResult(BigDecimal.valueOf(2));
        context.open();

        loopOperation.execute(context);
        context.close();

        Assert.assertEquals(context.getResult(), BigDecimal.valueOf(128));
    }

    @Test
    public void testDoWhileOperation() {
        ConditionLoopOperation loopOperation = new DoWhileOperationImpl();
        loopOperation.setOperation(multiplyOperation);
        loopOperation.setCalculator(calculatorFalse);

        MathOperationContext context = new MathOperationContext();
        context.setFirstParam(BigDecimal.valueOf(2));
        context.setResult(BigDecimal.valueOf(2));
        context.open();

        loopOperation.execute(context);
        context.close();

        Assert.assertEquals(context.getResult(), BigDecimal.valueOf(4));
    }
}
