package com.pem.test.composite;

import com.pem.core.operation.composite.CompositeOperation;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.*;
import com.pem.config.AppConfig;
import com.pem.core.context.OperationContextImpl;
import com.pem.core.operation.composite.SyncCompositeOperationImp;
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
public class SyncCompositeOperationTest {

    private Operation sumOperation;
    private Operation putToFirstOperation;
    private Operation putToSecondOperation;
    private Operation subtractOperation;

    @Before
    public void setUp() {
        sumOperation = new SumOperation();
        putToFirstOperation = new PutResultToFirstParam();
        putToSecondOperation = new PutResultToSecondParam();
        subtractOperation = new SubtractOperation();
    }

    @Test
    public void testSameOperations() {

        CompositeOperation operation = new SyncCompositeOperationImp();
        operation.addOperation(sumOperation); // 5 + 7 = 12
        operation.addOperation(putToFirstOperation);
        operation.addOperation(sumOperation); // 12 + 7 = 19
        operation.addOperation(putToSecondOperation);
        operation.addOperation(sumOperation); // 12 + 19 = 31
        operation.addOperation(putToFirstOperation);
        operation.addOperation(sumOperation); // 31 + 19 = 50
        operation.addOperation(putToSecondOperation);
        operation.addOperation(sumOperation); // 31 + 50 = 81

        MathOperationContext context =  new MathOperationContext(new OperationContextImpl());
        context.open();
        context.setFirstParam(BigDecimal.valueOf(5));
        context.setSecondParam(BigDecimal.valueOf(7));
        operation.execute(context);
        context.close();

        Assert.assertEquals(context.getResult(), BigDecimal.valueOf(81));
    }

}
