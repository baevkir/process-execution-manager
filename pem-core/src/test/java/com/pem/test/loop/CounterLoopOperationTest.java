package com.pem.test.loop;

import com.pem.common.*;
import com.pem.config.AppConfig;
import com.pem.operation.basic.Operation;
import com.pem.operation.loop.counter.CounterLoopOperation;
import com.pem.operation.loop.counter.CounterLoopOperationImpl;
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
public class CounterLoopOperationTest {

    private Operation multiplyOperation;

    @Before
    public void setUp() {

        multiplyOperation = new MultiplyOperation();


    }
    @Test
    public void testCounterLoopOperation() {
        CounterLoopOperation loopOperation = new CounterLoopOperationImpl();
        loopOperation.setCount(4);
        loopOperation.setOperation(multiplyOperation);

        MathOperationContext context = new MathOperationContext();
        context.setFirstParam(BigDecimal.valueOf(2));
        context.setResult(BigDecimal.valueOf(2));
        context.open();

        loopOperation.execute(context);
        context.close();

        Assert.assertEquals(context.getResult(), BigDecimal.valueOf(32));
    }
}
