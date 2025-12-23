package com.pragma.tecnologia.infrastructure.output.r2dbc.repository;

import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.ReporteBootcampEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface IReporteBootcampRepository extends R2dbcRepository<ReporteBootcampEntity, Long> {
}