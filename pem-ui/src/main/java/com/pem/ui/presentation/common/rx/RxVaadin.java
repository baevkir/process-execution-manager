package com.pem.ui.presentation.common.rx;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import org.springframework.util.Assert;
import rx.Observable;

public class RxVaadin {

    public static Observable<Button.ClickEvent> buttonClickObservable(Button button) {
        Assert.notNull(button);
        return Observable.create(subscriber -> button.addClickListener(event -> {
            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(event);
            }
        }));
    }

    public static Observable<ItemClickEvent> tableClickObservable(Table table) {
        return Observable.create(subscriber -> table.addItemClickListener(event -> {
            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(event);
            }
        }));
    }
}
