package com.pem.persistence.mongo.converter.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pem.core.common.converter.ConverterFactory;
import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationObject;
import com.pem.model.proccess.ExecutionProcessObject;
import com.pem.model.proccess.record.ExecutionRecordObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.mongo.model.proccess.record.ExecutionRecordEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromProcessEntityToObjectConverter extends ConverterTemplateMethods implements Converter<ExecutionProcessEntity, ExecutionProcessObject> {

    private ConverterFactory converterFactory;

    @Override
    public ExecutionProcessObject convert(ExecutionProcessEntity source) {
        ExecutionProcessObject executionProcessDTO = new ExecutionProcessObject();
        fillCommonFields(executionProcessDTO, source);

        OperationEntity operationEntity = deserializeExecutionOperation(source.getExecutionPlan());
        OperationObject operationDTO = converterFactory.convert(operationEntity, OperationObject.class);
        executionProcessDTO.setExecutionOperation(operationDTO);

        List<ExecutionRecordObject> executionRecordDTOs = new ArrayList<>();
        for (ExecutionRecordEntity executionRecordEntity:source.getExecutionRecords()) {
            ExecutionRecordObject executionRecordDTO = converterFactory.convert(executionRecordEntity, ExecutionRecordObject.class);
            executionRecordDTOs.add(executionRecordDTO);
        }
        executionProcessDTO.setExecutionRecords(executionRecordDTOs);

        return executionProcessDTO;
    }


    private OperationEntity deserializeExecutionOperation(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, OperationEntity.class);
        } catch (IOException exception) {
            throw new RuntimeException("Can`t deserialize Execution Operation " + json, exception);
        }
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
