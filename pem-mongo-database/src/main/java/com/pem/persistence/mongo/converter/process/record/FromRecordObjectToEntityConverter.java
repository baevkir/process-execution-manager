package com.pem.persistence.mongo.converter.process.record;

import com.pem.core.common.converter.Converter;
import com.pem.core.common.converter.RegisterInConverterFactory;
import com.pem.model.proccess.record.ExecutionRecordObject;
import com.pem.persistence.mongo.common.PemMongoConstants;
import com.pem.persistence.mongo.converter.common.ConverterTemplateMethods;
import com.pem.persistence.mongo.model.proccess.record.ExecutionRecordEntity;

@RegisterInConverterFactory(factories = PemMongoConstants.CONVERTER_FACTORY_NAME)
public class FromRecordObjectToEntityConverter extends ConverterTemplateMethods implements Converter<ExecutionRecordObject, ExecutionRecordEntity> {

    @Override
    public ExecutionRecordEntity convert(ExecutionRecordObject source) {
        ExecutionRecordEntity recordEntity = new ExecutionRecordEntity();
        recordEntity.setId(source.getId());
        recordEntity.setPk(source.getPk());
        recordEntity.setState(source.getState());

        return recordEntity;
    }
}
