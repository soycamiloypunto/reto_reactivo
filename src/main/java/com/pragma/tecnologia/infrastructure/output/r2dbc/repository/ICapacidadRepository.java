package com.pragma.tecnologia.infrastructure.output.r2dbc.repository;

import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.CapacidadEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapacidadRepository extends R2dbcRepository<CapacidadEntity, Long> {
    Mono<Boolean> existsByNombre(String nombre);

    @Query("SELECT c.* FROM capacidad c " +
            "LEFT JOIN capacidad_tecnologia ct ON c.id = ct.capacidad_id " +
            "GROUP BY c.id " +
            "ORDER BY " +
            "CASE WHEN :sortField = 'nombre' AND :direction = 'asc' THEN c.nombre END ASC, " +
            "CASE WHEN :sortField = 'nombre' AND :direction = 'desc' THEN c.nombre END DESC, " +
            "CASE WHEN :sortField = 'tecnologias' AND :direction = 'asc' THEN COUNT(ct.tecnologia_id) END ASC, " +
            "CASE WHEN :sortField = 'tecnologias' AND :direction = 'desc' THEN COUNT(ct.tecnologia_id) END DESC " +
            "LIMIT :size OFFSET :offset")
    Flux<CapacidadEntity> findAllCustom(String sortField, String direction, int size, int offset);

    @Query("SELECT c.* FROM capacidad c " +
            "INNER JOIN bootcamp_capacidad bc ON c.id = bc.capacidad_id " +
            "WHERE bc.bootcamp_id = :bootcampId")
    Flux<CapacidadEntity> findAllByBootcampId(Long bootcampId);

    //HU6
    @Query("SELECT COUNT(*) FROM bootcamp_capacidad WHERE capacidad_id = :id")
    Mono<Long> countBootcampsByCapacidadId(Long id);
}
