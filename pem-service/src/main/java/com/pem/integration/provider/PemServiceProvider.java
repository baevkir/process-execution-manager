package com.pem.integration.provider;

import com.pem.logic.rx.eventbus.ServiceEventBus;
import com.pem.logic.service.calculator.CalculatorService;

public interface PemServiceProvider {
    CalculatorService getCalculatorService();
    ServiceEventBus getServiceEventBus();
}
