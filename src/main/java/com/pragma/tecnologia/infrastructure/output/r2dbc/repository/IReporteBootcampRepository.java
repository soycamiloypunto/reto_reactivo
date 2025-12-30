package com.pragma.tecnologia.infrastructure.output.r2dbc.repository;

import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.ReporteBootcampEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface IReporteBootcampRepository extends R2dbcRepository<ReporteBootcampEntity, Long> {
    @Modifying // Indica que es un UPDATE/DELETE
    @Query("UPDATE reporte_bootcamp SET cantidad_inscritos = cantidad_inscritos + 1 WHERE bootcamp_id = :bootcampId")
    Mono<Integer> incrementarInscritos(Long bootcampId);
}