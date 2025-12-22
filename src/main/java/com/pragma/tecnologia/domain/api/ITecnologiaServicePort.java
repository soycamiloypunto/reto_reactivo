package com.pragma.tecnologia.domain.api;

import com.pragma.tecnologia.domain.model.Tecnologia;
import reactor.core.publisher.Mono;

public interface ITecnologiaServicePort {
    Mono<Void> registrarTecnologia(Tecnologia tecnologia);
}
