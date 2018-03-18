package com.pem.ui.presentation.operation.popup.bean.presenter;

import com.pem.core.common.annotation.spring.PrototypeComponent;
import com.pem.model.operation.bean.BeanOperationObject;
import com.pem.ui.presentation.common.presenter.AbstractPresenter;
import com.pem.ui.presentation.operation.popup.bean.view.BeanOperationEditor;
import com.pem.ui.presentation.operation.popup.bean.view.BeanOperationView;
import com.vaadin.data.Binder;

@PrototypeComponent
public class BeanOperationPresenterImpl extends AbstractPresenter<BeanOperationView> implements BeanOperationPresenter {

    private Binder<BeanOperationObject> binder = new Binder<>(BeanOperationObject.class);

    @Override
    public Binder bind(BeanOperationObject operation) {
        BeanOperationEditor editor = getView().getEditor();
        binder.bind(editor.getNameField(),
                operationObject -> operationObject.getName(),
                (operationObject, name) -> operationObject.setName(name));

        binder.bind(editor.getDescriptionField(),
                operationObject -> operationObject.getDescription(),
                (operationObject, description) -> operationObject.setDescription(description));

        binder.bind(editor.getNameField(),
                operationObject -> operationObject.getName(),
                (operationObject, name) -> operationObject.setName(name));

        binder.bind(editor.getBeanField(),
                operationObject -> operationObject.getBean().getName(),
                (operationObject, name) -> operationObject.getBean().setName(name));

        binder.readBean(operation);
        return binder;
    }
}
