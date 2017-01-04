package com.pem.core.rx.event;

import rx.Notification;
import rx.Observer;
import rx.functions.Action1;
import rx.internal.util.ActionNotificationObserver;
import rx.observers.Observers;

public abstract class ObservableEvent<S> extends BaseEvent {
    private Observer<S> observer;

    public void setObserver(Observer<S> observer) {
        this.observer = observer;
    }

    public void setNotificationObserver(Action1<Notification<S>> onNotification) {
        this.observer = new ActionNotificationObserver(onNotification);
    }

    public Observer<S> getObserver() {
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
