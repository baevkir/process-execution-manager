package com.pem.persistence.mongo.converter.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pem.core.common.converter.factory.ConverterFactory;
import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.operation.common.OperationDTO;
import com.pem.model.proccess.ExecutionProcessDTO;
import com.pem.model.proccess.record.ExecutionRecordDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.operation.common.OperationEntity;
import com.pem.persistence.mongo.model.proccess.ExecutionProcessEntity;
import com.pem.persistence.mongo.model.proccess.record.ExecutionRecordEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromProcessEntityToDTOConverter  extends ConverterTemplateMethods implements Converter<ExecutionProcessEntity, ExecutionProcessDTO> {

    private ConverterFactory converterFactory;

    @Override
    public ExecutionProcessDTO convert(ExecutionProcessEntity source) {
        ExecutionProcessDTO executionProcessDTO = new ExecutionProcessDTO();
        fillCommonFields(executionProcessDTO, source);

        OperationEntity operationEntity = deserializeExecutionOperation(source.getExecutionPlan());
        OperationDTO operationDTO = converterFactory.convert(operationEntity, OperationDTO.class);
        executionProcessDTO.setExecutionOperation(operationDTO);

        List<ExecutionRecordDTO> executionRecordDTOs = new ArrayList<>();
        for (ExecutionRecordEntity executionRecordEntity:source.getExecutionRecords()) {
            ExecutionRecordDTO executionRecordDTO = converterFactory.convert(executionRecordEntity, ExecutionRecordDTO.class);
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
