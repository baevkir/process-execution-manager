package com.pem.model.common;

public abstract class BaseDTOWithStatus extends BaseDTO {
    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
