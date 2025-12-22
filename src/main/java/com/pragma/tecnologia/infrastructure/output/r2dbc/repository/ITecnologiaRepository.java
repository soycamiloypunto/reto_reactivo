package com.pragma.tecnologia.infrastructure.output.r2dbc.repository;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.TecnologiaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITecnologiaRepository extends ReactiveCrudRepository<Tecnologia, Long> {
    Mono<Boolean> existsByNombre(String nombre);

    @Query("SELECT t.* FROM tecnologia t " +
            "JOIN capacidad_tecnologia ct ON t.id = ct.tecnologia_id " +
            "WHERE ct.capacidad_id = :capacidadId")
    Flux<TecnologiaEntity> findAllByCapacidadId(Long capacidadId);
}
