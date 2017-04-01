package com.pem.ui.presentation.common.reactor;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;



public class VaadinReactor {

    public static Flux<Button.ClickEvent> buttonClickPublisher(Button button) {
        Assert.notNull(button);
        return Flux.create(emitter ->  button.addClickListener(event -> emitter.next(event)));
    }

    public static Flux<ItemClickEvent> tableClickPublisher(Table table) {
        return Flux.create(emitter -> table.addItemClickListener(event -> emitter.next(event)));
    }

    public static Flux<Property.ValueChangeEvent> binderEditPublisher(BeanFieldGroup binder) {
        return Flux.fromIterable(binder.getFields()).flatMap(field -> fieldEditPublisher(field));
    }

    private static Flux<Property.ValueChangeEvent> fieldEditPublisher(Field<?> field) {
        return Flux.create(emitter ->  field.addValueChangeListener(event -> emitter.next(event)));
    }

    public static Flux<FieldGroup.CommitEvent> binderPostCommitPublisher(BeanFieldGroup binder) {
        return Flux.create(emitter -> binder.addCommitHandler(new FieldGroup.CommitHandler() {
            @Override
            public void preCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
            }

            @Override
            public void postCommit(FieldGroup.CommitEvent commitEvent) throws FieldGroup.CommitException {
                emitter.next(commitEvent);
            }
        }));
    }
}
