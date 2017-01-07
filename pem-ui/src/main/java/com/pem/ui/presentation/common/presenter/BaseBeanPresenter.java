package com.pem.ui.presentation.common.presenter;

import com.pem.ui.presentation.common.rx.RxVaadin;
import com.pem.ui.presentation.common.view.BeanView;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

public abstract class BaseBeanPresenter<B, V extends BeanView<B>> extends BasePresenter<V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasePresenter.class);

    private BeanFieldGroup<B> binder;
    private Observable<Property.ValueChangeEvent> editBinderObservable;
    private Observable<FieldGroup.CommitEvent> binderPostCommitObservable;

    @Override
    protected final void initViewHandlers() {
        getView().getBeanSubject().subscribe(bean -> bindBean(bean));
    }

    protected void bindBean(B bean) {
        binder = new BeanFieldGroup<>((Class<B>)bean.getClass());
        binder.setItemDataSource(bean);
        binder.setBuffered(true);
        binder.bindMemberFields(getView());

        getView().redrawForm();

        getView().getSubmitObservable()
                .filter(event -> getBinder().isModified())
                .subscribe(clickEvent -> commit());

        getView().getCancelObservable()
                .filter(event -> getBinder().isModified())
                .subscribe(clickEvent -> discard());

        editBinderObservable = RxVaadin.binderEditObservable(getBinder());

        getEditBinderObservable()
                .subscribe(valueChangeEvent -> getView().getBottomToolbar().setVisible(true));

        binderPostCommitObservable = RxVaadin.binderPostCommitObservable(getBinder());
    }

    protected BeanFieldGroup<B> getBinder() {
        return binder;
    }

    protected B getBean() {
        return getBinder().getItemDataSource().getBean();
    }

    protected Observable<Property.ValueChangeEvent> getEditBinderObservable() {
        return editBinderObservable;
    }

    protected Observable<FieldGroup.CommitEvent> getBinderPostCommitObservable() {
        return binderPostCommitObservable;
    }

    protected void commit() {
        LOGGER.trace("Start to commit {}.", getClass());
        try {
            getBinder().commit();
            getView().getBottomToolbar().setVisible(false);
        } catch (FieldGroup.CommitException exception) {
            throw new RuntimeException(exception);
        }
    }

    protected void discard() {
        getBinder().discard();
        getView().getBottomToolbar().setVisible(false);
    }


}
