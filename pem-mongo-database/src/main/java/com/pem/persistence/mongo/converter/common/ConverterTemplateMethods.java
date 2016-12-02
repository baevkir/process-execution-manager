package com.pem.persistence.mongo.converter.common;

import com.pem.model.common.BaseDTO;
import com.pem.persistence.mongo.model.common.BaseEntity;

public abstract class ConverterTemplateMethods {

    protected void fillCommonFields(BaseEntity baseEntity, BaseDTO baseDTO) {
        baseEntity.setCreatedWhen(baseDTO.getCreatedWhen());
        baseEntity.setModifyWhen(baseDTO.getModifyWhen());
        baseEntity.setId(baseDTO.getId());
        baseEntity.setName(baseDTO.getName());
        baseEntity.setDescription(baseDTO.getDescription());
    }

    protected void fillCommonFields(BaseDTO baseDTO, BaseEntity baseEntity) {
        baseDTO.setCreatedWhen(baseEntity.getCreatedWhen());
        baseDTO.setModifyWhen(baseEntity.getModifyWhen());
        baseDTO.setId(baseEntity.getId());
        baseDTO.setName(baseEntity.getName());
        baseDTO.setDescription(baseEntity.getDescription());
    }
}
