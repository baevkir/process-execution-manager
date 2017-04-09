package com.pem.persistence.api.service.common;

public interface CrudService<O> extends
        CreateService<O>,
        UpdateService<O>,
        GetByIdService<O>,
        GetAllService<O>,
        GetAllByTypeService<O>,
        DeleteService {
}
