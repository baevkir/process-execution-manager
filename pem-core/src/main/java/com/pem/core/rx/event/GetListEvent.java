package com.pem.core.rx.event;

import java.util.List;

public abstract class GetListEvent<S> extends ObservableEvent<S, List<S>> {
    public GetListEvent() {
        super(null);
    }
}
