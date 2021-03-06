package com.pem.logic.common.proxy;

import com.pem.core.context.OperationContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class OperationContextProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationContextProxy.class);

    @Pointcut("within(com.pem.core.operation.basic.Operation+)")
    public void heirOfOperationInterface() {}

    @Around("heirOfOperationInterface() && execution(* *execute(com.pem.core.context.OperationContext))")
    public Object executeOperationInOpenContext(ProceedingJoinPoint joinPoint) throws Throwable  {
        OperationContext context = (OperationContext) joinPoint.getArgs()[0];
        if (context.isOpen()) {
            LOGGER.trace("Context already open run without any actions.");
            return joinPoint.proceed();
        }

        LOGGER.trace("Start to Open context in {}.", joinPoint.getThis().getClass());
        context.open();
        try {
            return joinPoint.proceed();
        }finally {
            context.close();
        }
    }

}
