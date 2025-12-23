package com.pragma.tecnologia.domain.api;

import com.pragma.tecnologia.domain.model.Matricula;
import reactor.core.publisher.Mono;

public interface IMatriculaServicePort {
    Mono<Void> inscribirUsuario(Matricula matricula);
}