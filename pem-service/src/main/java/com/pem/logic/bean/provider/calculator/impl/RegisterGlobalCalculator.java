package com.pem.logic.bean.provider.calculator.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterGlobalCalculator {
    String value() default "";
    String[] executors() default "";
    boolean all() default false;
}
