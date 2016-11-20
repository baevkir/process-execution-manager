package com.pem.persistence.converter.process;

import com.pem.common.utils.ApplicationContextWrapper;
import com.pem.common.utils.IdGenerator;
import com.pem.common.utils.NamingUtils;
import com.pem.operation.basic.Operation;
import com.pem.persistence.converter.common.Converter;
import com.pem.persistence.converter.common.RegisterInConverterFactory;
import com.pem.persistence.model.common.bean.BeanEntity;
import com.pem.persistence.model.operation.basic.BeanOperationEntity;
import com.pem.persistence.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.model.proccess.record.ExecutionRecordEntity;
import com.pem.persistence.model.proccess.record.ExecutionRecordPK;
import com.pem.persistence.model.proccess.record.ExecutionRecordState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Collections;

@RegisterInConverterFactory(factoryName = "converterFactory")
public class FromOperationToProcessConverter implements Converter<Operation, ExecutionProcessEntity> {

    @Autowired
    private ApplicationContext context;

    @Override
    public ExecutionProcessEntity convert(Operation source) {
        ApplicationContextWrapper contextWrapper = new ApplicationContextWrapper(context);
        String beanName = contextWrapper.getBeanName(source);
        String name = NamingUtils.getHumanReadableName(beanName);

        ExecutionProcessEntity processEntity = new ExecutionProcessEntity();
        processEntity.setId(IdGenerator.generateId());
        processEntity.setName(name);

        BeanEntity beanEntity = new BeanEntity();
        beanEntity.setName(name);
        beanEntity.setBeanName(beanName);

        BeanOperationEntity beanOperationEntity = new BeanOperationEntity();
        beanOperationEntity.setId(IdGenerator.generateId());
        beanOperationEntity.setName(name);
        beanOperationEntity.setBean(beanEntity);

        ExecutionRecordEntity executionRecordEntity = new ExecutionRecordEntity();
        executionRecordEntity.setId(IdGenerator.generateId());
        executionRecordEntity.setState(ExecutionRecordState.CREATED);

        ExecutionRecordPK pk = new ExecutionRecordPK();
        pk.setOperationId(beanOperationEntity.getId());
        pk.setProcessId(processEntity.getId());
        executionRecordEntity.setPk(pk);

        processEntity.setExecutionOperation(beanOperationEntity);
        processEntity.setExecutionRecords(Collections.singletonList(executionRecordEntity));

        return processEntity;
    }

}
