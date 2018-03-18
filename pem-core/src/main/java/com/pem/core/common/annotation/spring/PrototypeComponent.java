package com.pem.core.common.annotation.spring;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Prototype
public @interface PrototypeComponent {
    String value() default "";
}
