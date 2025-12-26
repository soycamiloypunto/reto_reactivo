package com.pragma.tecnologia.domain.spi;

import com.pragma.tecnologia.domain.model.Capacidad;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapacidadPersistencePort {
    Mono<Capacidad> guardar(Capacidad capacidad);
    Mono<Boolean> existePorNombre(String nombre);
    Flux<Capacidad> listarCapacidades(int page, int size, String sortField, String direction);
}
