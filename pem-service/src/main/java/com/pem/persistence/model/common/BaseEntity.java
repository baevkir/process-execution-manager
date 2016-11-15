package com.pem.persistence.model.common;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigInteger;

public class BaseEntity {

    @Id
    private BigInteger id;

    @Indexed
    private String name;

    private String description;

    @Version
    private Long version;

    @CreatedDate
    private DateTime createdWhen;

    @LastModifiedDate
    private DateTime modifyWhen;

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public DateTime getCreatedWhen() {
        return createdWhen;
    }

    public void setCreatedWhen(DateTime createdWhen) {
        this.createdWhen = createdWhen;
    }

    public DateTime getModifyWhen() {
        return modifyWhen;
    }

    public void setModifyWhen(DateTime modifyWhen) {
        this.modifyWhen = modifyWhen;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdWhen=" + createdWhen +
                ", modifyWhen=" + modifyWhen +
                '}';
    }
}
