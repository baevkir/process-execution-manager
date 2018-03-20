package com.pem.core.common.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation marks in which {@link LaunchEventBus} should implements {@link LaunchEventHandler}
 * Make sens only on classes implemented interface {@link LaunchEventHandler}
 *
 * @see LaunchEventBus
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterLaunchEventHandler {
    String facade();
}
