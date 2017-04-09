package com.pem.persistence.api.service.common;

import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface DeleteService {
    Mono<Void> delete(BigInteger id);
}
