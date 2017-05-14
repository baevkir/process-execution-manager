package com.pem.test.operation;

import com.pem.config.AppConfig;
import com.pem.core.context.OperationContext;
import com.pem.core.operation.basic.Operation;
import com.pem.core.operation.basic.util.AnnotationOperation;
import com.pem.core.operation.basic.util.reflection.annotions.OperationMethod;
import com.pem.core.operation.basic.util.reflection.annotions.Param;
import com.pem.logic.MathOperationContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Objects;

import static com.pem.logic.MathOperationContext.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class AnnotatedOperationTest {

    @Test
    public void testAnnotatedOperationWithoutAnnotation() {
        Operation operation = new TestOperationWithoutAnnotation();
        StepVerifier.create(executeOperation(operation))
                .verifyError(RuntimeException.class);
    }

    @Test
    public void testAnnotatedOperationCheckResultParam() {
        Operation operation = new TestOperationResultTen();
        MathOperationContext context = new MathOperationContext();
        context.open();
        context.setFirstParam(BigDecimal.TEN);
        StepVerifier.create(executeOperation(operation, context))
                .expectNextMatches(operationContext -> Objects.equals(operationContext.getContextParam(RESULT_PARAM, String.class), BigDecimal.TEN.toString()))
                .verifyComplete();

    }

    @Test
    public void testAnnotatedOperationWithoutMandatoryParam() {
        Operation operation = new TestOperationTwoParams();
        MathOperationContext context = new MathOperationContext();
        context.open();
        context.setFirstParam(BigDecimal.TEN);

        StepVerifier.create(executeOperation(operation, context))
                .verifyError(NullPointerException.class);
    }

    @Test
    public void testAnnotatedOperation() {
        Operation operation = new TestOperationTwoParams();
        MathOperationContext context = new MathOperationContext();
        context.open();
        context.setFirstParam(BigDecimal.valueOf(7));
        context.setSecondParam(BigDecimal.valueOf(5));
        StepVerifier.create(executeOperation(operation, context))
                .expectNextMatches(operationContext -> Objects.equals(operationContext.getResult(), BigDecimal.valueOf(12)))
                .verifyComplete();
    }

    @Test
    public void testAnnotatedOperationChild() {
        Operation operation = new ChildTestOperationTwoParams();
        MathOperationContext context = new MathOperationContext();
        context.open();
        context.setFirstParam(BigDecimal.valueOf(7));
        context.setSecondParam(BigDecimal.valueOf(5));

        StepVerifier.create(executeOperation(operation, context))
                .expectNextMatches(operationContext -> !operationContext.isOpen())
                .verifyComplete();
    }

    private <C extends OperationContext> Mono<C> executeOperation(Operation operation, C context) {
        return operation.execute(context)
                .map(operationContext -> context)
                .doOnSuccess(operationContext -> operationContext.close());
    }

    private Mono<OperationContext> executeOperation(Operation operation) {
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
