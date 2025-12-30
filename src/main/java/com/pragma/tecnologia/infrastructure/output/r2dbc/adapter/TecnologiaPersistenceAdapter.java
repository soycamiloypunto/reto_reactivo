package com.pragma.tecnologia.infrastructure.output.r2dbc.adapter;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.domain.spi.ITecnologiaPersistencePort;
import com.pragma.tecnologia.infrastructure.output.r2dbc.mapper.ITecnologiaEntityMapper;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.ITecnologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TecnologiaPersistenceAdapter implements ITecnologiaPersistencePort {

    private final ITecnologiaRepository iTecnologiaRepository;
    private final ITecnologiaEntityMapper iTecnologiaEntityMapper;

    // HU1
    @Override
    public Mono<Tecnologia> guardar(Tecnologia tecnologia) {
        return Mono.just(tecnologia)
                .map(iTecnologiaEntityMapper::toEntity)
                .flatMap(iTecnologiaRepository::save)
                .map(iTecnologiaEntityMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existePorNombre(String nombre) {
        return iTecnologiaRepository.existsByNombre(nombre);
    }

    // --- NUEVOS MÉTODOS PARA SOPORTAR HU6 (Eliminación en Cascada) ---

    @Override
    public Mono<Long> contarUsosEnCapacidades(Long tecnologiaId) {
        // Necesitas tener este método o un @Query en tu ITecnologiaRepository
        // SELECT COUNT(*) FROM capacidad_tecnologia WHERE tecnologia_id = :id
        return iTecnologiaRepository.countCapacidadesByTecnologiaId(tecnologiaId);
    }

    @Override
    public Mono<Void> eliminarTecnologia(Long tecnologiaId) {
        // Simplemente borra por ID. La lógica de "si es huérfana" ya la hizo el UseCase.
        return iTecnologiaRepository.deleteById(tecnologiaId);
    }
}