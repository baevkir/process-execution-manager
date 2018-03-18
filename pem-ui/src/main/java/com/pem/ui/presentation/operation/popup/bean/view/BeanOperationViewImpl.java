package com.pem.ui.presentation.operation.popup.bean.view;

import com.pem.model.operation.bean.BeanOperationObject;
import com.pem.ui.integration.viewprovider.BeanFormView;
import com.pem.ui.presentation.operation.popup.bean.presenter.BeanOperationPresenter;
import com.pem.ui.presentation.operation.popup.common.view.AbstractPopupView;
import com.vaadin.data.Binder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@BeanFormView(BeanOperationObject.class)
public class BeanOperationViewImpl extends AbstractPopupView<BeanOperationObject, BeanOperationEditor> implements BeanOperationView{
    private BeanOperationEditor editor;
    private BeanOperationPresenter presenter;

    @Override
    public BeanOperationEditor getEditor() {
        return editor;
    }

    @Override
    public Binder<BeanOperationObject> bind(BeanOperationObject object) {
        Binder<BeanOperationObject> binder = presenter.bind(object);
        redraw();
        binder.setReadOnly(true);
        return binder;
    }

    @Override
    public void redraw() {
        removeAllComponents();
        addComponent(editor.getNameField());
        addComponent(editor.getBeanField());
        addComponent(editor.getDescriptionField());
        addComponent(editor.getCreatedWhenField());
        addComponent(editor.getModifyWhenField());

    }

    @PostConstruct
    void init() {
        presenter.setView(this);
        redraw();
    }

    @Autowired
    public void setEditor(BeanOperationEditor editor) {
        this.editor = editor;
    }
    @Autowired
    public void setPresenter(BeanOperationPresenter presenter) {
        this.presenter = presenter;
    }
}
