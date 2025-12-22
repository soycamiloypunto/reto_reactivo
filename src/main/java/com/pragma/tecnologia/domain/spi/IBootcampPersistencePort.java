package com.pragma.tecnologia.domain.spi;

import com.pragma.tecnologia.domain.model.Bootcamp;
import reactor.core.publisher.Mono;

public interface IBootcampPersistencePort {
    Mono<Void> guardarBootcamp(Bootcamp bootcamp);
}