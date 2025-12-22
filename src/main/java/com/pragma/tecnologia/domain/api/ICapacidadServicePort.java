package com.pragma.tecnologia.domain.api;

import com.pragma.tecnologia.domain.model.Capacidad;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapacidadServicePort {
    Mono<Void> registrarCapacidad(Capacidad capacidad);
    // Importar Flux de Reactor
    Flux<Capacidad> listarCapacidades(int page, int size, String sortField, String direction);
}
