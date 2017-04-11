package com.pem.test.operation;

import com.pem.core.operation.basic.Operation;
import com.pem.core.context.OperationContext;
import com.pem.logic.MathOperationContext;
import com.pem.config.AppConfig;
import com.pem.core.operation.basic.util.AnnotationOperation;
import com.pem.core.operation.basic.util.reflection.annotions.OperationMethod;
import com.pem.core.operation.basic.util.reflection.annotions.Param;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static com.pem.logic.MathOperationContext.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class AnnotatedOperationTest {

    @Test(expected = RuntimeException.class)
    public void testAnnotatedOperationWithoutAnnotation() {
        Operation operation = new TestOperationWithoutAnnotation();
        executeOperation(operation);
    }

    @Test
    public void testAnnotatedOperationCheckResultParam() {
        Operation operation = new TestOperationResultTen();
        MathOperationContext context = new MathOperationContext();
        context.open();
        context.setFirstParam(BigDecimal.TEN);
        executeOperation(operation, context);
        Assert.assertEquals(context.getContextParam(RESULT_PARAM, String.class), BigDecimal.TEN.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testAnnotatedOperationWithoutMandatoryParam() {
        Operation operation = new TestOperationTwoParams();
        MathOperationContext context = new MathOperationContext();
        context.open();
        context.setFirstParam(BigDecimal.TEN);
        executeOperation(operation, context);
    }

    @Test
    public void testAnnotatedOperation() {
        Operation operation = new TestOperationTwoParams();
        MathOperationContext context = new MathOperationContext();
        context.open();
        context.setFirstParam(BigDecimal.valueOf(7));
        context.setSecondParam(BigDecimal.valueOf(5));
        executeOperation(operation, context);
        Assert.assertEquals(context.getResult(), BigDecimal.valueOf(12));
    }

    @Test(expected = RuntimeException.class)
    public void testAnnotatedOperationChild() {
        Operation operation = new ChildTestOperationTwoParams();
        MathOperationContext context = new MathOperationContext();
        context.open();
        context.setFirstParam(BigDecimal.valueOf(7));
        context.setSecondParam(BigDecimal.valueOf(5));
        executeOperation(operation, context);
    }

    private OperationContext executeOperation(Operation operation, OperationContext context) {
         return operation.execute(Mono.just(context))
                .doOnNext(operationContext -> operationContext.close()).block();
    }

    private OperationContext executeOperation(Operation operation) {
        OperationContext context = new MathOperationContext();
        context.open();
        return executeOperation(operation, context);
    }

    private class TestOperationWithoutAnnotation extends AnnotationOperation {

    }

    private class TestOperationResultTen extends AnnotationOperation {

        @OperationMethod(result = RESULT_PARAM)
        public String testOperation(@Param(FIRST_PARAM) BigDecimal firstParam) {
            return firstParam.toString();
        }
    }

    private class TestOperationTwoParams extends AnnotationOperation {

        @Param(value = FIRST_PARAM, mandatory = true)
        private BigDecimal firstParam;

        @OperationMethod(result = RESULT_PARAM)
        public BigDecimal testOperation(@Param(value = SECOND_PARAM, mandatory = true) BigDecimal secondParam) {
            Assert.assertNotNull(firstParam);
            Assert.assertNotNull(secondParam);
            return firstParam.add(secondParam);
        }
    }

    private class ChildTestOperationTwoParams extends TestOperationTwoParams {

    }
}
