package com.pragma.tecnologia.infrastructure.output.r2dbc.repository;

import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.BootcampEntity;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.MatriculaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMatriculaRepository extends R2dbcRepository<MatriculaEntity, Long> {

    // Regla 1: Validar máximo 5 bootcamps
    Mono<Long> countByIdUsuario(Long idUsuario);

    // Regla 2: Traer los bootcamps del usuario para validar cruce de fechas
    // Hacemos JOIN para obtener los detalles (fechas) de los cursos matriculados
    @Query("SELECT b.* FROM bootcamp b " +
            "INNER JOIN matricula m ON b.id = m.id_bootcamp " +
            "WHERE m.id_usuario = :idUsuario")
    Flux<BootcampEntity> findAllBootcampsByUsuarioId(Long idUsuario);

    // 1. Obtener el ID del bootcamp con más registros
    @Query("SELECT id_bootcamp FROM matricula GROUP BY id_bootcamp ORDER BY COUNT(*) DESC LIMIT 1")
    Mono<Long> findBootcampIdMasInscritos();

    // 2. Obtener los IDs de los usuarios de un bootcamp
    @Query("SELECT id_usuario FROM matricula WHERE id_bootcamp = :bootcampId")
    Flux<Long> findUsuariosInscritos(Long bootcampId);
}