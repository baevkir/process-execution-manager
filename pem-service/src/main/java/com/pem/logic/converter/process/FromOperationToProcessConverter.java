package com.pem.logic.converter.process;

import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.core.common.applicationcontext.ApplicationContextWrapper;
import com.pem.core.operation.basic.Operation;
import com.pem.logic.common.ServiceConstants;
import com.pem.logic.common.utils.IdGenerator;
import com.pem.core.common.applicationcontext.bean.BeanObject;
import com.pem.core.common.applicationcontext.bean.BeanObjectBuilder;
import com.pem.model.operation.bean.BeanOperationObject;
import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.model.proccess.record.ExecutionRecordObject;
import com.pem.model.proccess.record.ExecutionRecordPK;
import com.pem.model.proccess.record.ExecutionRecordState;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collections;

@RegisterInConverterFactory(factories = ServiceConstants.CONVERTER_FACTORY_NAME)
public class FromOperationToProcessConverter implements Converter<Operation, ExecutionProcessObject>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public ExecutionProcessObject convert(Operation source) {
        ApplicationContextWrapper contextWrapper = new ApplicationContextWrapper(applicationContext);
        String beanName = contextWrapper.getBeanName(source);
        BeanObject bean = BeanObjectBuilder.newInstance()
                .setBeanName(beanName)
                .build();

        ExecutionProcessObject processEntity = new ExecutionProcessObject();
        processEntity.setId(IdGenerator.generateId());
        processEntity.setName(bean.getName());


        BeanOperationObject beanOperationEntity = new BeanOperationObject();
        beanOperationEntity.setId(IdGenerator.generateId());
        beanOperationEntity.setName(bean.getName());
        beanOperationEntity.setBean(bean);

        ExecutionRecordObject executionRecordEntity = new ExecutionRecordObject();
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
