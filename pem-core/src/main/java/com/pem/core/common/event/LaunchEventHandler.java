package com.pem.core.common.event;

/**
 * Implementations of this interface invokes in the {@link LaunchEventBus}
 */
public interface LaunchEventHandler {
    void handle();
}
