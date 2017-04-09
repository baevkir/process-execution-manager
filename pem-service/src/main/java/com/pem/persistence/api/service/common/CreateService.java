package com.pem.persistence.api.service.common;

import reactor.core.publisher.Mono;

public interface CreateService<O> {
    Mono<O> create(O object);
}
