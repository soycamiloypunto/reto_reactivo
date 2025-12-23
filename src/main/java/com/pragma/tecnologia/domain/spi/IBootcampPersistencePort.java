package com.pragma.tecnologia.domain.spi;

import com.pragma.tecnologia.domain.model.Bootcamp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootcampPersistencePort {
    Mono<Bootcamp> guardarBootcamp(Bootcamp bootcamp); // Antes era Mono<Void> ahora es flux HU8
    Flux<Bootcamp> listarBootcamps(int page, int size, String sortField, String direction);
    Mono<Void> eliminarBootcamp(Long bootcampId);//HU6
    Mono<Bootcamp> obtenerBootcampPorId(Long id);//HU7
}