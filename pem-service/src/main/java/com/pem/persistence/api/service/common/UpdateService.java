package com.pem.persistence.api.service.common;

import reactor.core.publisher.Mono;

public interface UpdateService<O> {
    Mono<Void> update(O object);
}
