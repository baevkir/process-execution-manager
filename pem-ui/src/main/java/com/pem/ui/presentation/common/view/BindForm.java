package com.pem.ui.presentation.common.view;

import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.EventObject;

public abstract class BindForm<B> extends CustomComponent {

    private static final Method FORM_MODIFIED_METHOD = ReflectTools.findMethod(
            BindForm.FormModifiedListener.class, "formModified",
            BindForm.FormModifiedEvent.class);

    private BeanFieldGroup<B> binder;

    private boolean modified;

    public void bind(B bean) {
        modified = false;
        binder = new BeanFieldGroup<>((Class<B>)bean.getClass());
        binder.setItemDataSource(bean);
        binder.setBuffered(true);
        binder.bindMemberFields(this);
        addChangeHandler();
    }

    public boolean isModified() {
        return modified;
    }

    public void addFormModifiedListener(FormModifiedListener listener) {
        addListener(FormModifiedEvent.class, listener, FORM_MODIFIED_METHOD);
    }

    protected void setModified(boolean modified) {
        this.modified = modified;
        fireModifiedEvent();
    }

    protected B getBean() {
        return binder.getItemDataSource().getBean();
    }

    protected BeanFieldGroup getBinder() {
        return binder;
    }

    protected boolean isValid() {
        return binder.isValid();
    }

    protected void commit() {
        try {
            binder.commit();
            setModified(false);
        } catch (FieldGroup.CommitException exception) {
            throw new RuntimeException(exception);
        }
    }

    protected void discard() {
        binder.discard();
        setModified(false);
    }


    private void addChangeHandler() {
       for (final Field<?> field : binder.getFields()) {
            field.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    modified = true;
                    fireModifiedEvent(field);
                }
            });
        }
    }

    private void fireModifiedEvent() {
        fireModifiedEvent(null);
    }

    private void fireModifiedEvent(Field field) {
        fireEvent(new FormModifiedEvent(this, field));
    }

    public interface FormModifiedListener {
        void formModified(FormModifiedEvent event);
    }

    public static class FormModifiedEvent extends EventObject {

        private Field<?> field;

        public FormModifiedEvent(BindForm<?> form, Field field) {
            super(form);
            this.field = field;
        }

        public BindForm<?> getForm() {
            return (BindForm<?>) getSource();
        }

        public Field<?> getField() {
            return field;
        }
    }
}
