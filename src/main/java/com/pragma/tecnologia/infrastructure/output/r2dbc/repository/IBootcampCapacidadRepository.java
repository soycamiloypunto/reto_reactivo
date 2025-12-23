package com.pragma.tecnologia.infrastructure.output.r2dbc.repository;

import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.BootcampCapacidadEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface IBootcampCapacidadRepository extends R2dbcRepository<BootcampCapacidadEntity, Long> {
    Mono<Void> deleteAllByBootcampId(Long bootcampId);
}