package com.pem.persistence.model.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigInteger;

public class BaseEntity {

    @Id
    private BigInteger id;

    @Indexed
    private String name;

    private String description;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
