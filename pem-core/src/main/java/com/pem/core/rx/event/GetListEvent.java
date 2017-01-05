package com.pem.core.rx.event;

import java.util.List;

public abstract class GetListEvent<S> extends ObservableEvent<List<S>> {
    public GetListEvent() {
    }
}
