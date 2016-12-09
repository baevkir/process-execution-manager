package com.pem.persistence.mongo.converter.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.model.proccess.record.ExecutionRecordDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.mongo.model.proccess.record.ExecutionRecordEntity;

import java.util.ArrayList;
import java.util.List;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromProcessDTOToEntityConverter extends ConverterTemplateMethods implements Converter<ExecutionProcessDTO, ExecutionProcessEntity> {

    private ConverterFactory converterFactory;

    @Override
    public ExecutionProcessEntity convert(ExecutionProcessDTO source) {
        ExecutionProcessEntity executionProcessEntity = new ExecutionProcessEntity();
        fillCommonFields(executionProcessEntity, source);

        OperationEntity operationEntity = converterFactory.convert(source.getExecutionOperation(), OperationEntity.class);
        String executionPlan = serializeExecutionOperation(operationEntity);
        executionProcessEntity.setExecutionPlan(executionPlan);

        List<ExecutionRecordEntity> executionRecordEntities = new ArrayList<>();
        for (ExecutionRecordDTO executionRecord : source.getExecutionRecords()) {
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
