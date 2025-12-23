package com.pragma.tecnologia.domain.spi;

import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Matricula;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMatriculaPersistencePort {
    Mono<Void> guardarMatricula(Matricula matricula);
    Mono<Long> contarMatriculasUsuario(Long idUsuario);
    Flux<Bootcamp> obtenerBootcampsInscritos(Long idUsuario);
}