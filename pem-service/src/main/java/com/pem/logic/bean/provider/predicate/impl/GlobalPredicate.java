package com.pem.logic.bean.provider.predicate.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalPredicate {
    String value() default "";
    String[] executors() default "";
    boolean all() default false;
}
