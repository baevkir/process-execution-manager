package com.pem.core.rx.event;

import io.reactivex.Single;

public abstract class SingleEvent<T> extends ReactiveEvent<T> {

    public void observe(Single<T> single) {
        observeObservable(single.toObservable());
    }

    public Single<T> getSingle() {
        return getLogedObservable().singleOrError();
    }
}
