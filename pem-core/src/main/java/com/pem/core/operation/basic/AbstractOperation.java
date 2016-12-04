package com.pem.core.operation.basic;

import java.math.BigInteger;

public abstract class AbstractOperation implements Operation {

    private BigInteger id;

    @Override
    public BigInteger getId() {
        return id;
    }

    @Override
    public void setId(BigInteger id) {
        this.id = id;
    }

}
