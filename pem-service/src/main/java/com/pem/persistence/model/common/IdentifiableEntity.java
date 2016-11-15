package com.pem.persistence.model.common;

import org.springframework.data.annotation.Id;

import java.math.BigInteger;

public abstract class IdentifiableEntity {
    @Id
    private BigInteger id;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdentifiableEntity{" +
                "id=" + id +
                '}';
    }
}
