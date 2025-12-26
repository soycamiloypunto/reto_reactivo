package com.pragma.tecnologia.domain.api;

import com.pragma.tecnologia.domain.model.Capacidad;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapacidadServicePort {
    //HU2
    Mono<Void> registrarCapacidad(Capacidad capacidad);
    Flux<Capacidad> listarCapacidades(int page, int size, String sortField, String direction);
}
