package com.pem.persistence.api.service.common;

import reactor.core.publisher.Flux;

public interface GetAllByTypeService<O> {
    <T extends O> Flux<T> getAllByType(Class<T> type);
}
