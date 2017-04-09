package com.pem.ui.presentation.common.view.provider;

import com.pem.model.operation.common.OperationObject;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class OperationViewObject {
    private String name;
    private Class<? extends OperationObject> operationType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends OperationObject> getOperationType() {
        return operationType;
    }

    public void setOperationType(Class<? extends OperationObject> operationType) {
        this.operationType = operationType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;

        if (object == null || getClass() != object.getClass()) return false;

        OperationViewObject that = (OperationViewObject) object;

        return new EqualsBuilder()
                .append(getOperationType(), that.getOperationType())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getOperationType())
                .toHashCode();
    }
}
