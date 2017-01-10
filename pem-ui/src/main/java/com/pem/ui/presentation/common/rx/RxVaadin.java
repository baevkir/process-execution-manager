package com.pem.ui.presentation.common.rx;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import org.springframework.util.Assert;


public class RxVaadin {

    public static Observable<Button.ClickEvent> buttonClickObservable(Button button) {
        Assert.notNull(button);
        return Observable.create(emitter ->
                button.addClickListener(event -> onNext(emitter, event)));
    }

    public static Observable<ItemClickEvent> tableClickObservable(Table table) {
        return Observable.create(emitter ->
                table.addItemClickListener(event -> onNext(emitter, event)));
    }

    public static Observable<Property.ValueChangeEvent> binderEditObservable(BeanFieldGroup binder) {
        return Observable.create(emitter ->
                binder.getFields().forEach(field ->
                        field.addValueChangeListener(event -> onNext(emitter, event))));

    }

    public static Observable<FieldGroup.CommitEvent> binderPostCommitObservable(BeanFieldGroup binder) {
        return Observable.create(emitter -> binder.addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                onNext(emitter, commitEvent);
            }
        }));
    }

    private static <T> void onNext(ObservableEmitter<T> emitter, T value) {
        emitter.onNext(value);
    }
}
