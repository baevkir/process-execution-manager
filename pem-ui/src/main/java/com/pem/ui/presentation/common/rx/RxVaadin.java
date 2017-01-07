package com.pem.ui.presentation.common.rx;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import org.springframework.util.Assert;
import rx.Observable;
import rx.Subscriber;

public class RxVaadin {

    public static Observable<Button.ClickEvent> buttonClickObservable(Button button) {
        Assert.notNull(button);
        return Observable.create(subscriber ->
                button.addClickListener(event -> onNext(subscriber, event)));
    }

    public static Observable<ItemClickEvent> tableClickObservable(Table table) {
        return Observable.create(subscriber ->
                table.addItemClickListener(event -> onNext(subscriber, event)));
    }

    public static Observable<Property.ValueChangeEvent> binderEditObservable(BeanFieldGroup binder) {
        return Observable.create(subscriber ->
                binder.getFields().forEach(field ->
                        field.addValueChangeListener(event -> onNext(subscriber, event))));

    }

    public static Observable<FieldGroup.CommitEvent> binderPostCommitObservable(BeanFieldGroup binder) {
        return Observable.create(subscriber -> binder.addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                onNext(subscriber, commitEvent);
            }
        }));
    }

    private static <T> void onNext(Subscriber<T> subscriber, T value) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onNext(value);
        }
    }
}
