package com.pem.operation.basic;

public abstract class AbstractOperation implements Operation {

    private String id;

    @Override
    public String getOperationId() {
        return id;
    }

    @Override
    public void setOperationId(String id) {
        this.id = id;
    }

}
