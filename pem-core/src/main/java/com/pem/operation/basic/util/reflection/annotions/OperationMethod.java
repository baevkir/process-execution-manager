package com.pem.operation.basic.util.reflection.annotions;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationMethod {
    String result() default "";
}
