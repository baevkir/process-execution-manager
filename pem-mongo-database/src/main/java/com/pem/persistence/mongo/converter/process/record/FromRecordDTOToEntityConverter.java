package com.pem.persistence.mongo.converter.process.record;

import com.pem.core.common.converter.impl.Converter;
import com.pem.core.common.converter.impl.RegisterInConverterFactory;
import com.pem.model.proccess.record.ExecutionRecordDTO;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.proccess.record.ExecutionRecordEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromRecordDTOToEntityConverter extends ConverterTemplateMethods implements Converter<ExecutionRecordDTO, ExecutionRecordEntity> {

    @Override
    public ExecutionRecordEntity convert(ExecutionRecordDTO source) {
        ExecutionRecordEntity recordEntity = new ExecutionRecordEntity();
        recordEntity.setId(source.getId());
        recordEntity.setPk(source.getPk());
        recordEntity.setState(source.getState());

        return recordEntity;
    }
}
