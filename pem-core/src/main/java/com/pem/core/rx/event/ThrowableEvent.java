package com.pem.core.rx.event;

public class ThrowableEvent extends BaseEvent {
    private Throwable throwable;

    public ThrowableEvent(Throwable throwable) {
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
