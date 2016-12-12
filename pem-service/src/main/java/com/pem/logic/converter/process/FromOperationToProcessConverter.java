package com.pem.logic.converter.process;

import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.core.common.utils.ApplicationContextWrapper;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.common.ServiceConstants;
import com.pem.logic.common.utils.IdGenerator;
import com.pem.core.common.bean.BeanObject;
import com.pem.core.common.bean.BeanObjectBuilder;
import com.pem.model.operation.bean.BeanOperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.model.proccess.record.ExecutionRecordDTO;
import com.pem.model.proccess.record.ExecutionRecordPK;
import com.pem.model.proccess.record.ExecutionRecordState;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collections;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class FromOperationToProcessConverter implements Converter<Operation, ExecutionProcessDTO>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public ExecutionProcessDTO convert(Operation source) {
        ApplicationContextWrapper contextWrapper = new ApplicationContextWrapper(applicationContext);
        String beanName = contextWrapper.getBeanName(source);
        BeanObject bean = BeanObjectBuilder.newInstance()
                .setBeanName(beanName)
                .build();

        ExecutionProcessDTO processEntity = new ExecutionProcessDTO();
        processEntity.setId(IdGenerator.generateId());
        processEntity.setName(bean.getName());


        BeanOperationDTO beanOperationEntity = new BeanOperationDTO();
        beanOperationEntity.setId(IdGenerator.generateId());
        beanOperationEntity.setName(bean.getName());
        beanOperationEntity.setBean(bean);

        ExecutionRecordDTO executionRecordEntity = new ExecutionRecordDTO();
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
