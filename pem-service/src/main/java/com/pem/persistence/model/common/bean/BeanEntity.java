package com.pem.persistence.model.common.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class BeanEntity {
    private String name;
    private String beanName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String toString() {
        return "BeanEntity{" +
                "name='" + name + '\'' +
                ", beanName='" + beanName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        BeanEntity that = (BeanEntity) object;

        return new EqualsBuilder()
                .append(getBeanName(), that.getBeanName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getBeanName())
                .toHashCode();
    }
}
