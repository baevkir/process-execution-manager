package com.pem.model.common;

import java.math.BigInteger;

public abstract class IdentifiableDTO {

    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdentifiableDTO{" +
                "id=" + id +
                '}';
    }
}
