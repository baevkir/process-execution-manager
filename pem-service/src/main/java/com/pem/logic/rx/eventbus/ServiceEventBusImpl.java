package com.pem.logic.rx.eventbus;

import com.pem.core.rx.event.BaseEvent;
import com.pem.core.rx.eventbus.RxEvenBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceEventBusImpl extends RxEvenBus<BaseEvent> implements ServiceEventBus {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEventBusImpl.class);
}
