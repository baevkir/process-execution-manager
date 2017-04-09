package com.pem.model.common;

public abstract class BaseObjectWithStatus extends BaseObject {
    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
