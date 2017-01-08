package com.pem.core.rx.event;

import rx.Notification;
import rx.Observer;
import rx.functions.Action1;
import rx.internal.util.ActionNotificationObserver;
import rx.observers.Observers;

public abstract class ObservableEvent<S, T> extends BaseEvent<S> {
    private Observer<T> observer;

    public ObservableEvent(S source) {
        super(source);
    }

    public ObservableEvent<S, T> setObserver(Observer<T> observer) {
        this.observer = observer;
        return this;
    }

    public ObservableEvent<S, T> setNotificationObserver(Action1<Notification<T>> onNotification) {
        return setObserver(new ActionNotificationObserver(onNotification));
    }

    public Observer<T> getObserver() {
        if (observer == null) {
            synchronized (ObservableEvent.class) {
                if (observer == null) {
                    observer = Observers.empty();
                }
            }
        }
        return observer;
    }
}
