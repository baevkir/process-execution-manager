package com.pem.persistence.api.service.common;

import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface GetByIdService<O>{
    Mono<O> getById(BigInteger id);
}
