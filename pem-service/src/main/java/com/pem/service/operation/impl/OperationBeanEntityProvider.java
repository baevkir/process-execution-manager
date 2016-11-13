package com.pem.service.operation.impl;

import com.pem.persistence.model.common.bean.BeanEntity;

import java.util.List;

public interface OperationBeanEntityProvider {
    List<BeanEntity> provideOperationBeanEntity();
}
