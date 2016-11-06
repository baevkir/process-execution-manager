package com.pem.database.model.common.bean;

public class OperationBeanEntity {
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
        return "OperationBeanEntity{" +
                "name='" + name + '\'' +
                ", beanName='" + beanName + '\'' +
                '}';
    }
}
