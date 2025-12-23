package com.pragma.tecnologia.infrastructure.output.r2dbc.adapter;

import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Matricula;
import com.pragma.tecnologia.domain.spi.IMatriculaPersistencePort;
import com.pragma.tecnologia.infrastructure.output.r2dbc.mapper.IBootcampEntityMapper;
import com.pragma.tecnologia.infrastructure.output.r2dbc.mapper.IMatriculaEntityMapper;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.IMatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MatriculaPersistenceAdapter implements IMatriculaPersistencePort {

    private final IMatriculaRepository matriculaRepository;
    private final IMatriculaEntityMapper matriculaEntityMapper;
    private final IBootcampEntityMapper bootcampEntityMapper; // Para mapear los resultados del JOIN

    @Override
    public Mono<Void> guardarMatricula(Matricula matricula) {
        return Mono.just(matricula)
                .map(matriculaEntityMapper::toEntity)
                .flatMap(matriculaRepository::save)
                .then();
    }

    @Override
    public Mono<Long> contarMatriculasUsuario(Long idUsuario) {
        return matriculaRepository.countByIdUsuario(idUsuario);
    }

    @Override
    public Flux<Bootcamp> obtenerBootcampsInscritos(Long idUsuario) {
        // La query devuelve BootcampEntity, usamos el mapper simple para pasarlo a Dominio
        return matriculaRepository.findAllBootcampsByUsuarioId(idUsuario)
                .map(bootcampEntityMapper::toDomainSimple);
    }

    @Override
    public Mono<Long> obtenerBootcampIdMasInscritos() {
        return matriculaRepository.findBootcampIdMasInscritos();
    }

    @Override
    public Flux<Long> obtenerIdsUsuariosInscritos(Long bootcampId) {
        return matriculaRepository.findUsuariosInscritos(bootcampId);
    }
}