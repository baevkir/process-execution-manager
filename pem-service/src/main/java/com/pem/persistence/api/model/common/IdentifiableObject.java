package com.pem.persistence.api.model.common;

import java.math.BigInteger;

public abstract class IdentifiableObject {

    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdentifiableObject{" +
                "id=" + id +
                '}';
    }
}
