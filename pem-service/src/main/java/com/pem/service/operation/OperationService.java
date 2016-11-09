package com.pem.service.operation;

import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.persistence.model.operation.common.OperationEntity;

import java.math.BigInteger;
import java.util.List;

public interface OperationService {
    OperationEntity createOperation(OperationEntity operationEntity);
    void updateOperation(OperationEntity operationEntity);
    OperationEntity getOperation(BigInteger id);
    List<OperationEntity> getAllOperation();
    void runOperation(BigInteger id);
    List<BeanEntity> getAllOperationBeanEntities();
}
