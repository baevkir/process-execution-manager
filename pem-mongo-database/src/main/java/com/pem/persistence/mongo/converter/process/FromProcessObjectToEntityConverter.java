package com.pem.persistence.mongo.converter.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pem.core.common.converter.ConverterFactory;
import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.model.proccess.record.ExecutionRecordObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.mongo.model.proccess.record.ExecutionRecordEntity;

import java.util.ArrayList;
import java.util.List;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromProcessObjectToEntityConverter extends ConverterTemplateMethods implements Converter<ExecutionProcessObject, ExecutionProcessEntity> {

    private ConverterFactory converterFactory;

    @Override
    public ExecutionProcessEntity convert(ExecutionProcessObject source) {
        ExecutionProcessEntity executionProcessEntity = new ExecutionProcessEntity();
        fillCommonFields(executionProcessEntity, source);

        OperationEntity operationEntity = converterFactory.convert(source.getExecutionOperation(), OperationEntity.class);
        String executionPlan = serializeExecutionOperation(operationEntity);
        executionProcessEntity.setExecutionPlan(executionPlan);

        List<ExecutionRecordEntity> executionRecordEntities = new ArrayList<>();
        for (ExecutionRecordObject executionRecord : source.getExecutionRecords()) {
            ExecutionRecordEntity executionRecordDTO = converterFactory.convert(executionRecord, ExecutionRecordEntity.class);
            executionRecordEntities.add(executionRecordDTO);
        }
        executionProcessEntity.setExecutionRecords(executionRecordEntities);

        return executionProcessEntity;
    }

    private String serializeExecutionOperation(OperationEntity operation) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(operation);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Can`t serialize Execution Operation " + operation, exception);
        }
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
