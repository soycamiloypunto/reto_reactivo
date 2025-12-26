package com.pragma.tecnologia.domain.spi;

import com.pragma.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Mono;

public interface ITecnologiaPersistencePort {
    //HU1
    Mono<Tecnologia> guardar(Tecnologia tecnologia);
    Mono<Boolean> existePorNombre(String nombre);
}