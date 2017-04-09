package com.pem.persistence.mongo.converter.common;

import com.pem.model.common.BaseObject;
import com.pem.model.common.BaseObjectWithStatus;
import com.pem.persistence.mongo.model.common.BaseEntity;

public abstract class ConverterTemplateMethods {

    protected void fillCommonFields(BaseEntity baseEntity, BaseObject baseDTO) {
        baseEntity.setId(baseDTO.getId());
        baseEntity.setName(baseDTO.getName());
        baseEntity.setDescription(baseDTO.getDescription());
    }

    protected void fillCommonFields(BaseObject baseDTO, BaseEntity baseEntity) {
        baseDTO.setCreatedWhen(baseEntity.getCreatedWhen());
        baseDTO.setModifyWhen(baseEntity.getModifyWhen());
        baseDTO.setId(baseEntity.getId());
        baseDTO.setName(baseEntity.getName());
        baseDTO.setDescription(baseEntity.getDescription());
    }

    protected void checkActive(BaseObjectWithStatus rootBaseDTO, BaseObjectWithStatus baseDTO) {
        if (!rootBaseDTO.isActive() || baseDTO.isActive()) {
            return;
        }

        rootBaseDTO.setActive(baseDTO.isActive());
    }
}
