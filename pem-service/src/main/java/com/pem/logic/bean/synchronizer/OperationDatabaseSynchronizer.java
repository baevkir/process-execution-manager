package com.pem.logic.bean.synchronizer;

import com.pem.logic.bean.provider.operation.OperationProvider;
import com.pem.logic.bean.provider.operation.impl.RegisterGlobalOperation;
import com.pem.logic.common.utils.NamingUtils;
import com.pem.operation.basic.Operation;
import com.pem.model.common.bean.BeanObject;
import com.pem.persistence.api.service.operation.OperationPersistenceService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OperationDatabaseSynchronizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationDatabaseSynchronizer.class);

    private OperationProvider operationProvider;
    private OperationPersistenceService operationPersistenceService;

    public void setOperationProvider(OperationProvider operationProvider) {
        this.operationProvider = operationProvider;
    }

    public void setOperationPersistenceService(OperationPersistenceService operationPersistenceService) {
        this.operationPersistenceService = operationPersistenceService;
    }

    @PostConstruct
    public void synchronizeOperationsWithDatabase() {
//        List<OperationDTO> operationEntities = operationPersistenceService.getOperationsByType(BeanOperationDTO.class);
        List<BeanObject> operations = provideOperationBeanEntity();
        for (BeanObject operation : operations) {
            
        }
    }

    public List<BeanObject> provideOperationBeanEntity() {
        LOGGER.debug("Get All OperationBeanEntity .");
        List<BeanObject> operations = new ArrayList<>();

        Map<String, Operation> beans = operationProvider.getAllGlobalOperations();
        for (Map.Entry<String, Operation> entry : beans.entrySet()) {
            BeanObject operation = new BeanObject();
            String beanName = entry.getKey();
            LOGGER.trace("Add bean with name {}", beanName);
            operation.setBeanName(beanName);

            Class clazz = AopProxyUtils.ultimateTargetClass(entry.getValue());
            RegisterGlobalOperation annotation = (RegisterGlobalOperation) clazz.getAnnotation(RegisterGlobalOperation.class);
            String name = annotation.value();
            if (StringUtils.isEmpty(name)) {
                name = NamingUtils.getHumanReadableName(beanName);
            }
            LOGGER.trace("Presentation name for bean {}", name);
            operation.setName(name);

            operations.add(operation);
        }

        return operations;
    }
}
