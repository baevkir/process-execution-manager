package com.pem.test.state;

import com.pem.operation.basic.Operation;
import com.pem.logic.*;
import com.pem.config.AppConfig;
import com.pem.operation.composite.SyncCompositeOperationImp;
import com.pem.operation.condition.ConditionOperation;
import com.pem.operation.condition.IntegerConditionOperationImpl;
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
public class StateOperationTest {

    private Operation switchOperation;
    private Operation subtractOperation;
    private ConditionOperation conditionOperation;

    @Before
    public void setUp() {
        switchOperation = new SwitchParamsOperation();
        subtractOperation = new SubtractOperation();

        conditionOperation = new IntegerConditionOperationImpl();
        conditionOperation.addCondition(1, subtractOperation);
        conditionOperation.addCondition(0, subtractOperation);
        conditionOperation.addCondition(-1, getSwitchAndSubtractOperation());
        conditionOperation.setCalculator(new CompareFirstWithSecondCalculator());
    }

    @Test
    public void testFirstGreaterOperations() {
        MathOperationContext context = new MathOperationContext();
        context.open();
        context.setFirstParam(BigDecimal.valueOf(5));
        context.setSecondParam(BigDecimal.valueOf(3));

        conditionOperation.execute(context);
        context.close();
        Assert.assertEquals(context.getResult(), BigDecimal.valueOf(2));
    }

    @Test
    public void testFirstEqualOperations() {
        MathOperationContext context = new MathOperationContext();
        context.open();
        context.setFirstParam(BigDecimal.valueOf(15));
        context.setSecondParam(BigDecimal.valueOf(15));

        conditionOperation.execute(context);
        context.close();
        Assert.assertEquals(context.getResult(), BigDecimal.ZERO);
    }

    @Test
    public void testFirstLessOperations() {
        MathOperationContext context = new MathOperationContext();
        context.open();
        context.setFirstParam(BigDecimal.valueOf(12));
        context.setSecondParam(BigDecimal.valueOf(17));

        conditionOperation.execute(context);
        context.close();
        Assert.assertEquals(context.getResult(), BigDecimal.valueOf(5));
    }

    private Operation getSwitchAndSubtractOperation() {
        SyncCompositeOperationImp operation = new SyncCompositeOperationImp();
        operation.addOperation(switchOperation);
        operation.addOperation(subtractOperation);
        return operation;
    }
}
