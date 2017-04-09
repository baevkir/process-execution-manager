package com.pem.persistence.api.service.common;

import reactor.core.publisher.Flux;

public interface GetAllService<O> {
    Flux<O> getAll();
}
