package com.pem.persistence.mongo.converter.process.record;

import com.pem.core.converter.impl.Converter;
import com.pem.core.converter.impl.RegisterInConverterFactory;
import com.pem.model.proccess.record.ExecutionRecordDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.proccess.record.ExecutionRecordEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromRecordEntityToDTOConverter extends ConverterTemplateMethods implements Converter<ExecutionRecordEntity, ExecutionRecordDTO> {

    @Override
    public ExecutionRecordDTO convert(ExecutionRecordEntity source) {
        ExecutionRecordDTO recordDTO = new ExecutionRecordDTO();
        recordDTO.setId(source.getId());
        recordDTO.setPk(source.getPk());
        recordDTO.setState(source.getState());
        recordDTO.setModifyWhen(source.getModifyWhen());
        recordDTO.setCreatedWhen(source.getCreatedWhen());

        return recordDTO;
    }
}
