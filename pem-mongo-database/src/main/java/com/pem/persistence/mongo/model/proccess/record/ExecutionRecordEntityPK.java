package com.pem.persistence.mongo.model.proccess.record;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.math.BigInteger;

public class ExecutionRecordEntityPK {
    private BigInteger operationId;
    private BigInteger processId;

    public BigInteger getOperationId() {
        return operationId;
    }

    public void setOperationId(BigInteger operationId) {
        this.operationId = operationId;
    }

    public BigInteger getProcessId() {
        return processId;
    }

    public void setProcessId(BigInteger processId) {
        this.processId = processId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        ExecutionRecordEntityPK that = (ExecutionRecordEntityPK) object;

        return new EqualsBuilder()
                .append(getOperationId(), that.getOperationId())
                .append(getProcessId(), that.getProcessId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getOperationId())
                .append(getProcessId())
                .toHashCode();
    }
}
