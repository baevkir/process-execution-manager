package com.pem.model.common;

import org.joda.time.DateTime;

public abstract class BaseDTO extends IdentifiableDTO {

    private String name;

    private String description;

    private DateTime createdWhen;

    private DateTime modifyWhen;

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
        return super.toString() + '\'' +
                "BaseDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdWhen=" + createdWhen +
                ", modifyWhen=" + modifyWhen +
                "} ";
    }
}
