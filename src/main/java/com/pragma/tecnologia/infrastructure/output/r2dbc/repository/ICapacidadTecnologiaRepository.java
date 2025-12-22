package com.pragma.tecnologia.infrastructure.output.r2dbc.repository;

import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.CapacidadTecnologiaEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ICapacidadTecnologiaRepository extends R2dbcRepository<CapacidadTecnologiaEntity, Long> {
    // Busca todas las relaciones vinculadas a una capacidad específica
    Flux<CapacidadTecnologiaEntity> findAllByCapacidadId(Long capacidadId);

}
