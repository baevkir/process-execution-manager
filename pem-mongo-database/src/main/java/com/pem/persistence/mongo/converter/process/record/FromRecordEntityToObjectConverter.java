package com.pem.persistence.mongo.converter.process.record;

import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.model.proccess.record.ExecutionRecordObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.proccess.record.ExecutionRecordEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromRecordEntityToObjectConverter extends ConverterTemplateMethods implements Converter<ExecutionRecordEntity, ExecutionRecordObject> {

    @Override
    public ExecutionRecordObject convert(ExecutionRecordEntity source) {
        ExecutionRecordObject recordDTO = new ExecutionRecordObject();
        recordDTO.setId(source.getId());
        recordDTO.setPk(source.getPk());
        recordDTO.setState(source.getState());
        recordDTO.setModifyWhen(source.getModifyWhen());
        recordDTO.setCreatedWhen(source.getCreatedWhen());

        return recordDTO;
    }
}
