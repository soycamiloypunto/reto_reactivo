package com.pragma.tecnologia.infrastructure.output.r2dbc.repository;

import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.BootcampEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface IBootcampRepository extends R2dbcRepository<BootcampEntity, Long> {

    @Query("SELECT b.* FROM bootcamp b " +
            "LEFT JOIN bootcamp_capacidad bc ON b.id = bc.bootcamp_id " +
            "GROUP BY b.id " +
            "ORDER BY " +
            "CASE WHEN :sortField = 'nombre' AND :direction = 'asc' THEN b.nombre END ASC, " +
            "CASE WHEN :sortField = 'nombre' AND :direction = 'desc' THEN b.nombre END DESC, " +
            "CASE WHEN :sortField = 'capacidades' AND :direction = 'asc' THEN COUNT(bc.capacidad_id) END ASC, " +
            "CASE WHEN :sortField = 'capacidades' AND :direction = 'desc' THEN COUNT(bc.capacidad_id) END DESC " +
            "LIMIT :size OFFSET :offset")
    Flux<BootcampEntity> findAllCustom(String sortField, String direction, int size, int offset);
}
