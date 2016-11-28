package com.pem.test.proxy;


import com.pem.common.CompareFirstWithSecondCalculator;
import com.pem.common.MathOperationContext;
import com.pem.common.bean.provider.operation.OperationProvider;
import com.pem.context.OperationContext;
import com.pem.context.OperationContextImpl;
import com.pem.operation.basic.Operation;
import com.pem.operation.composite.CompositeOperation;
import com.pem.operation.composite.SyncCompositeOperation;
import com.pem.operation.condition.IntegerConditionOperation;
import com.pem.test.common.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    private OperationProvider provider;

    @Test
    public void testProxyOpenContext() {
        OperationContext context = new OperationContextImpl();
        checkOpenContextOperation.execute(context);
    }


    @Test
    public void testProxyOpenContextOnBaseOperation() {
        MathOperationContext context = new MathOperationContext();
        context.setFirstParam(BigDecimal.valueOf(12));
        context.setSecondParam(BigDecimal.valueOf(13));
        CompositeOperation compositeOperation = provider.createCommonOperation(SyncCompositeOperation.class);
        compositeOperation.addOperation(checkOpenContextOperation);
        compositeOperation.addOperation(sumOperation);
        compositeOperation.addOperation(checkOpenContextOperation);
        compositeOperation.execute(context);
        Assert.assertEquals(context.getResult(), BigDecimal.valueOf(25));
    }

    @Test
    public void testProxyOpenContextOnIntegerConditionOperation() {
        MathOperationContext context = new MathOperationContext();
        context.setFirstParam(BigDecimal.valueOf(12));
        context.setSecondParam(BigDecimal.valueOf(13));
        IntegerConditionOperation conditionOperation = provider.createCommonOperation(IntegerConditionOperation.class);
        conditionOperation.addCondition(1, subtractOperation);
        conditionOperation.addCondition(0, subtractOperation);
        conditionOperation.addCondition(-1, sumOperation);
        conditionOperation.setCalculator(new CompareFirstWithSecondCalculator());
        conditionOperation.execute(context);
        Assert.assertEquals(context.getResult(), BigDecimal.valueOf(25));
    }
}
