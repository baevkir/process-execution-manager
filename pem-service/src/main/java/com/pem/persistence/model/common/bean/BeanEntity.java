package com.pem.persistence.model.common.bean;

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
}
