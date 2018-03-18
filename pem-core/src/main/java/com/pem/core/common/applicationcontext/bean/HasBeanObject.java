package com.pem.core.common.applicationcontext.bean;

/**
 * Interface marker. Marks that class keeps reference to spring bean
 */
public interface HasBeanObject {
    BeanObject getBean();
}
