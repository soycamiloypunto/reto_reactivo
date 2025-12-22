package com.pragma.tecnologia.domain.api;

import com.pragma.tecnologia.domain.model.Bootcamp;
import reactor.core.publisher.Mono;

public interface IBootcampServicePort { // O IBootcampPersistencePort
    Mono<Void> guardarBootcamp(Bootcamp bootcamp);
}
