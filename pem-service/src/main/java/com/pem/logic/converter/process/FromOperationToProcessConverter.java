package com.pem.logic.converter.process;

import com.pem.logic.common.Constants;
import com.pem.logic.common.utils.ApplicationContextWrapper;
import com.pem.logic.common.utils.IdGenerator;
import com.pem.logic.common.utils.NamingUtils;
import com.pem.operation.basic.Operation;
import com.pem.logic.converter.common.Converter;
import com.pem.logic.converter.common.RegisterInConverterFactory;
import com.pem.model.common.bean.BeanObject;
import com.pem.model.operation.basic.BeanOperationDTO;
import com.pem.model.proccess.ExecutionProcess;
import com.pem.model.proccess.record.ExecutionRecord;
import com.pem.model.proccess.record.ExecutionRecordPK;
import com.pem.model.proccess.record.ExecutionRecordState;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collections;

@RegisterInConverterFactory(factories = Constants.CONVERTER_FACTORY_NAME)
public class FromOperationToProcessConverter implements Converter<Operation, ExecutionProcess>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public ExecutionProcess convert(Operation source) {
        ApplicationContextWrapper contextWrapper = new ApplicationContextWrapper(applicationContext);
        String beanName = contextWrapper.getBeanName(source);
        String name = NamingUtils.getHumanReadableName(beanName);

        ExecutionProcess processEntity = new ExecutionProcess();
        processEntity.setId(IdGenerator.generateId());
        processEntity.setName(name);

        BeanObject beanEntity = new BeanObject();
        beanEntity.setName(name);
        beanEntity.setBeanName(beanName);

        BeanOperationDTO beanOperationEntity = new BeanOperationDTO();
        beanOperationEntity.setId(IdGenerator.generateId());
        beanOperationEntity.setName(name);
        beanOperationEntity.setBean(beanEntity);

        ExecutionRecord executionRecordEntity = new ExecutionRecord();
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
