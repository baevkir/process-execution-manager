package com.pem.common.proxy;

import com.pem.common.provider.OperationProviderImpl;
import com.pem.context.OperationContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OperationContextProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationProviderImpl.class);

    @Pointcut("within(com.pem.operation.basic.Operation+)")
    public void heirOfOperationInterface() {}

    @Around("heirOfOperationInterface() && execution(* *execute(com.pem.context.OperationContext))")
    public void executeOperationInOpenContext(ProceedingJoinPoint joinPoint) throws Throwable  {
        OperationContext context = (OperationContext) joinPoint.getArgs()[0];
        if (context.isOpen()) {
            LOGGER.trace("Context already open run without any actions.");
            joinPoint.proceed();
            return;
        }

        LOGGER.trace("Start to Open context in {}.", joinPoint.getThis().getClass());
        context.open();
        try {
            joinPoint.proceed();
        }finally {
            context.close();
        }
    }

}
